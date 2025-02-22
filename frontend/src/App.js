import { Route, Routes } from 'react-router-dom';
import DashboardLayout from './layouts/dashboard/DashboardLayout';
import Login from './pages/auth/Login';
import Signup from './pages/auth/Signup';
import Unauthorized from './pages/auth/Unauthorized';
import Dashboard from './pages/dashboard/Dashboard';
import Transaction from './pages/transaction/Transaction';
import BasicTabs from './pages/transfer/BasicTabs';
import AddFunds from './pages/wallet/AddFunds';
import Wallet from './pages/wallet/Wallet';
import PrivateRoute from './PrivateRoute';
import ProtectedRoute from './ProtectedRoute';
import CardPage from './pages/Card/Card';
import NewCard from './pages/Card/NewCard';
import GenerateCredentials from './pages/wallet/GenerateCredentials';

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />

      <Route path="unauthorized" element={<PrivateRoute />}>
        <Route index element={<Unauthorized />} />
      </Route>

      <Route path="/" element={<PrivateRoute />}>
        <Route path="" element={<DashboardLayout />}>
          <Route path="" element={<Dashboard />}>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route index element={<Dashboard />} />
            </Route>
          </Route>

          <Route path="wallet" element={<PrivateRoute />}>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route index element={<Wallet />} />
            </Route>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route path="addFunds" element={<AddFunds />} />
            </Route>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route path="generateCredentials" element={<GenerateCredentials />} />
            </Route>
          </Route>

          <Route path="cards" element={<PrivateRoute />}> {/* Add the cards route */}
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route index element={<CardPage />} />
            </Route>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route path="new" element={<NewCard />} />
            </Route>
          </Route>

          <Route path="transfers" element={<PrivateRoute />}>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route index element={<BasicTabs />} />
            </Route>
          </Route>

          <Route path="transactions" element={<PrivateRoute />}>
            <Route element={<ProtectedRoute roles={['ROLE_USER', 'ROLE_ADMIN']} />}>
              <Route index element={<Transaction />} />
            </Route>
          </Route>
        </Route>
      </Route>
    </Routes>
  );
}
