import {
  Button,
  Card,
  Container,
  Stack,
  Typography,
} from '@mui/material';
import { enqueueSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';
import Iconify from '../../components/iconify';
import AuthService from '../../services/AuthService';
import HttpService from '../../services/HttpService';

export default function Wallet() {
  const [data, setData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth(`/wallets/users/${userId}`)
      .then((response) => {
        setData(response.data[0]); // Assuming the first wallet is the only wallet
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

  if (!data) return null;

  return (
    <>
      <Helmet>
        <title> Wallet | e-Wallet </title>
      </Helmet>
      <Container sx={{ minWidth: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}>
        <Card sx={{ padding: 5, textAlign: 'center' }}>
          <Typography variant="h3" gutterBottom>
            My Wallet
          </Typography>
          <Typography variant="h4" gutterBottom>
            Balance: {data.balance}
          </Typography>
          <Typography variant="h6" gutterBottom>
            IBAN: {data.iban}
          </Typography>
          <Stack spacing={2} direction="row" justifyContent="center">
            <Button
              variant="contained"
              startIcon={<Iconify icon="eva:plus-fill" />}
              onClick={() => navigate('/wallet/addFunds')}
              size="large"
            >
              Add Funds
            </Button>
            <Button
              variant="contained"
              startIcon={<Iconify icon="eva:key-fill" />}
              onClick={() => navigate('/wallet/generateCredentials')}
              size="large"
            >
              Generate One Time Credentials
            </Button>
          </Stack>
        </Card>
      </Container>
    </>
  );
}
