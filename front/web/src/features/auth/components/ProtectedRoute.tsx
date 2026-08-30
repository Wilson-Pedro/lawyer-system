import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Role } from '../../../config/roles';
import { SideBar } from '../../../components/SideBar/SideBar';

interface ProtectedRouteProps {
  allowedRoles?: Role[];
}

export const ProtectedRoute = ({ allowedRoles }: ProtectedRouteProps) => {
  const { user, isAuthenticated, isLoading } = useAuth();

  // 1. Enquanto o useEffect do AuthContext está lendo o localStorage,
  // mostramos um loading para não redirecionar a pessoa indevidamente.
  if (isLoading) {
    return (
      <div
        style={{ display: 'flex', justifyContent: 'center', marginTop: '50px' }}
      >
        <p>Carregando...</p> {/* Substitua por um Spinner/Loader */}
      </div>
    );
  }

  // 2. Se o loading acabou e não tem usuário logado, chuta pro login
  if (!isAuthenticated || !user) {
    return <Navigate to="/auth/login" replace />;
  }

  // 3. Se a rota exige um perfil específico e o usuário não tem, barra o acesso
  if (allowedRoles && !allowedRoles.includes(user.role)) {
    return <Navigate to="/acesso-negado" replace />;
  }

  // 4. Se passou por todas as barreiras, renderiza a rota filha
  return <Outlet />;
};
