import {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from 'react';

import { api } from '../../../config/api';
import { User } from '../types/Auth';

interface AuthContextData {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (token: string, userData: User) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextData | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  // isLoading começa como true para evitar redirecionamento p/ tela de login
  // antes do React ler o localStorage
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Verficica se já tem uma sessão salva no localStorage
    const carregarSessao = async () => {
      const storedToken = localStorage.getItem('@AppJuridico:token');

      if (storedToken) {
        try {
          const response = await api.get('/auth/me');
          setUser(response.data);
        } catch (error) {
          setUser(null);
        }
        setIsLoading(false);
      }
    };

    carregarSessao();
  }, []);

  const login = (token: string, userData: User) => {
    localStorage.setItem('@AppJuridico:token', token);

    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem('@SeuApp:token');
    // TODO: Verificar se é necessário deslogar no servidor também. Se sim, descomentar o código abaixo.
    // try {
    //   await api.post('/auth/logout');
    // } catch (error) {
    //   console.error('Erro ao deslogar no servidor', error);
    // } finally {
    //   localStorage.removeItem('@SeuApp:token');
    // }
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        isLoading,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (context === undefined) {
    throw new Error('useAuth deve ser usado dentro de um AuthProvider');
  }

  return context;
};
