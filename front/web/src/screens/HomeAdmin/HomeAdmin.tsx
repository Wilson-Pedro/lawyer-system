import React from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { 
    FileAltIcon, 
    SingOutAltIcon,
    FileCirclePlusIcon,
    PlusCircleIcon
} from "../../Icons/Icon";

interface MenuItem {
  label: string;
  icon: React.JSX.Element;
  path: string;
  variant: string;
}

export default function HomeAdmin() {
  const navigate = useNavigate();

  const menuItems: MenuItem[] = [
    { label: "Ver Processos", icon: <FileAltIcon />, path: "/processos", variant: "primary" },
    { label: "Cadastrar", icon: <PlusCircleIcon />, path: "/cadastrar", variant: "success" },
    { label: "Movimentar", icon: <FileCirclePlusIcon />, path: "/movimentar", variant: "secondary" },
    { label: "Sair", icon: <SingOutAltIcon />, path: "/", variant: "danger" },
  ];

  return (
    <div className="min-vh-100 d-flex flex-column bg-light">
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
        <span className="navbar-brand fw-bold fs-4">Painel Administrativo</span>
        <button
          className="btn btn-outline-light ms-auto"
          onClick={() => navigate("/")}
        >
          <SingOutAltIcon className="me-2" /> Sair
        </button>
      </nav>

      {/* Conteúdo */}
      <div className="container my-5 flex-grow-1">
        <div className="row g-4 justify-content-center">
          {menuItems.map((item, index) => (
            <div key={index} className="col-12 col-sm-6 col-md-4 col-lg-3">
              <div
                className={`card text-center border-0 shadow-sm h-100 bg-${item.variant} bg-opacity-75 text-white`}
                style={{
                  borderRadius: "1rem",
                  transition: "transform 0.2s, box-shadow 0.2s",
                  cursor: "pointer",
                }}
                onClick={() => navigate(item.path)}
                onMouseEnter={(e) => {
                  e.currentTarget.style.transform = "scale(1.03)";
                  e.currentTarget.style.boxShadow = "0 6px 20px rgba(0,0,0,0.2)";
                }}
                onMouseLeave={(e) => {
                  e.currentTarget.style.transform = "scale(1)";
                  e.currentTarget.style.boxShadow = "none";
                }}
              >
                <div className="card-body d-flex flex-column align-items-center justify-content-center p-4">
                  <div className="fs-1 mb-3">{item.icon}</div>
                  <h5 className="fw-semibold">{item.label}</h5>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Rodapé */}
      <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
        © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
      </footer>
    </div>
  );
};