import { LoadingButton } from '@mui/lab';
import { Autocomplete, Button, Card, Container, Grid, Stack, TextField, Typography } from '@mui/material';
import { useSnackbar } from 'notistack';
import { useState, useEffect } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';
import HttpService from '../../services/HttpService';

export default function AddFunds() {
  const defaultValues = {
    amount: '',
    description: '',
    typeId: 1, // set as Transfer by default
  };

  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [formValues, setFormValues] = useState(defaultValues);
  const [selectedCard, setSelectedCard] = useState(null);
  const [cards, setCards] = useState([]);

  useEffect(() => {
    fetchWalletData();
    fetchCardsData();
  }, []);

  const fetchWalletData = () => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth(`/wallets/users/${userId}`)
      .then((response) => {
        if (response.data && response.data.length > 0) {
          const wallet = response.data[0];
          setFormValues((prevValues) => ({
            ...prevValues,
            fromWalletIban: wallet.iban,
            toWalletIban: wallet.iban,
          }));
        }
      })
      .catch((error) => {
        enqueueSnackbar('Failed to fetch wallet data', { variant: 'error' });
      });
  };

  const fetchCardsData = () => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth(`/cards/users/${userId}`)
      .then((response) => {
        setCards(response.data.map(card => {
          const lastFourDigits = card.credentials.slice(-4);
          let cardType;

          switch (card.credentials[0]) {
            case '2':
            case '5':
              cardType = 'Mastercard';
              break;
            case '4':
              cardType = 'Visa';
              break;
            case '3':
              cardType = 'American Express';
              break;
            default:
              cardType = 'Wallet';
          }

          return {
            label: `${cardType} - **** **** **** ${lastFourDigits}`,
            id: card.id,
          };
        }));
      })
      .catch((error) => {
        if (error?.response?.status === 401) {
          navigate('/login');
        } else if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) => enqueueSnackbar(e.message, { variant: 'error' }));
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: 'error' });
        } else {
          enqueueSnackbar(error.message, { variant: 'error' });
        }
      });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleCardChange = (event, value) => {
    setSelectedCard(value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const requestPayload = { ...formValues };
    HttpService.postWithAuth('/wallets/addFunds', requestPayload)
      .then((response) => {
        enqueueSnackbar('Funds added successfully', { variant: 'success' });
        navigate('/wallet');
      })
      .catch((error) => {
        if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) => enqueueSnackbar(e.message, { variant: 'error' }));
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: 'error' });
        } else {
          enqueueSnackbar(error.message, { variant: 'error' });
        }
      });
  };

  return (
    <>
      <Helmet>
        <title> Add Funds | e-Wallet </title>
      </Helmet>
      <Container sx={{ minWidth: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}>
        <Card sx={{ padding: 5, textAlign: 'center' }}>
          <Typography variant="h4" gutterBottom>
            Add Funds
          </Typography>
          <Grid container alignItems="center" justifyContent="center" direction="column" sx={{ width: 400, padding: 5 }}>
            <Stack spacing={3} sx={{ width: '100%' }}>
              <TextField
                id="amount"
                name="amount"
                label="Amount"
                autoFocus
                required
                value={formValues.amount}
                onChange={handleInputChange}
                fullWidth
              />
              <TextField
                id="description"
                name="description"
                label="Description"
                autoComplete="description"
                required
                value={formValues.description}
                onChange={handleInputChange}
                fullWidth
              />
              <Autocomplete
                id="card"
                options={cards}
                getOptionLabel={(option) => option.label}
                renderInput={(params) => <TextField {...params} label="Select Card" />}
                onChange={handleCardChange}
                fullWidth
              />
            </Stack>
            <Stack spacing={2} direction="row" alignItems="center" justifyContent="center" sx={{ mt: 4, width: '100%' }}>
              <Button sx={{ width: '50%' }} size="large" variant="outlined" onClick={() => navigate('/wallet')}>
                Cancel
              </Button>
              <LoadingButton sx={{ width: '50%' }} size="large" type="submit" variant="contained" onClick={handleSubmit}>
                Save
              </LoadingButton>
            </Stack>
          </Grid>
        </Card>
      </Container>
    </>
  );
}
