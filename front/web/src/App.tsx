import { BrowserRouter } from 'react-router-dom';
import './App.css';
import Rotas from './AppRoutes';
import { AuthProvider } from './features/auth';
// import 'bootstrap/dist/css/bootstrap.min.css';

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Rotas />
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
