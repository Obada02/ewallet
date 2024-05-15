import { LoadingButton } from '@mui/lab';
import { Button, Card, Container, Grid, Stack, TextField, Typography } from '@mui/material';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { useSnackbar } from 'notistack';
import { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';
import HttpService from '../../services/HttpService';

export default function NewCard() {
  const defaultValues = {
    credentials: '',
    expiryDate: null,
    userId: AuthService.getCurrentUser()?.id,
  };

  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [formValues, setFormValues] = useState(defaultValues);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleDateChange = (date) => {
    setFormValues({
      ...formValues,
      expiryDate: date.toISOString(),  // Ensure the date is in ISO format
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    HttpService.postWithAuth('/cards', formValues)
      .then((response) => {
        enqueueSnackbar('Card created successfully', { variant: 'success' });
        navigate('/cards');
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
        <title> New Card | e-Wallet </title>
      </Helmet>
      <Container sx={{ minWidth: '100%' }}>
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={1}>
          <Typography variant="h4" gutterBottom>
            New Card
          </Typography>
        </Stack>
        <Card>
          <Grid container alignItems="left" justify="center" direction="column" sx={{ width: 400, padding: 5 }}>
            <Stack spacing={3}>
              <TextField
                id="credentials"
                name="credentials"
                label="Credentials"
                autoComplete="given-name"
                autoFocus
                required
                value={formValues.credentials}
                onChange={handleInputChange}
              />
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DatePicker
                  label="Expiry Date"
                  value={formValues.expiryDate}
                  onChange={handleDateChange}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      id="expiryDate"
                      name="expiryDate"
                      required
                    />
                  )}
                />
              </LocalizationProvider>
            </Stack>
            <Stack spacing={2} direction="row" alignItems="right" justifyContent="end" sx={{ mt: 4 }}>
              <Button sx={{ width: 120 }} variant="outlined" onClick={() => navigate('/cards')}>
                Cancel
              </Button>
              <LoadingButton sx={{ width: 120 }} size="large" type="submit" variant="contained" onClick={handleSubmit}>
                Save
              </LoadingButton>
            </Stack>
          </Grid>
        </Card>
      </Container>
    </>
  );
}
