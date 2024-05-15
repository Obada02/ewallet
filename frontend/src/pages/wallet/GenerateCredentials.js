import {
  Button,
  Card,
  Container,
  Stack,
  Typography,
} from '@mui/material';
import { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';
import { enqueueSnackbar } from 'notistack';

const generateRandomIban = () => {
  // A simple function to generate a random IBAN-like string (not a real IBAN)
  const randomChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  let result = 'DE';
  for (let i = 0; i < 20; i += 1) {
    result += randomChars.charAt(Math.floor(Math.random() * randomChars.length));
  }
  return result;
};

export default function GenerateCredentials() {
  const [generatedIban, setGeneratedIban] = useState(generateRandomIban());
  const navigate = useNavigate();

  const handleGenerateNew = () => {
    setGeneratedIban(generateRandomIban());
  };

  const handleCopyToClipboard = () => {
    navigator.clipboard.writeText(generatedIban).then(() => {
      enqueueSnackbar('IBAN copied to clipboard', { variant: 'success' });
    }).catch(() => {
      enqueueSnackbar('Failed to copy IBAN', { variant: 'error' });
    });
  };

  return (
    <>
      <Helmet>
        <title> Generate One Time Credentials | e-Wallet </title>
      </Helmet>
      <Container sx={{ minWidth: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}>
        <Card sx={{ padding: 5, textAlign: 'center' }}>
          <Typography variant="h3" gutterBottom>
            One Time Credentials
          </Typography>
          <Typography variant="h5" gutterBottom>
            Generated IBAN: {generatedIban}
          </Typography>
          <Stack spacing={2} direction="row" justifyContent="center" sx={{ mt: 3 }}>
            <Button
              variant="contained"
              onClick={handleGenerateNew}
              size="large"
            >
              Generate New
            </Button>
            <Button
              variant="outlined"
              onClick={handleCopyToClipboard}
              size="large"
            >
              Copy to Clipboard
            </Button>
            <Button
              variant="outlined"
              onClick={() => navigate('/wallet')}
              size="large"
            >
              Back to Wallet
            </Button>
          </Stack>
        </Card>
      </Container>
    </>
  );
}
