import { useAuth } from '../../features/auth';

export const SideBar = () => {
  const { user, logout } = useAuth();

  return (
    <aside>
      <div>
        <p>Olá, {user?.nome}</p>
        <p>Perfil: {user?.role}</p>
      </div>

      <nav>{/* Seus links de navegação aqui */}</nav>

      {/* Botão de deslogar */}
      <button onClick={logout} className="btn-sair">
        Sair do Sistema
      </button>
    </aside>
  );
};
