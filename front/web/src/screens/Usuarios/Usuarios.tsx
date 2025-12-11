import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EditIcon } from "../../Icons/Icon";

const API_URL = process.env.REACT_APP_API;

interface ResponseMinDto {
  id: string;
  nome: string;
  email: string;
  telefone: string;
  matricula: string;
  periodo: string;
  registro: string;
}

interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export default function Usuarios() {
  const [usuarios, setUsuarios] = useState<ResponseMinDto[]>([]);
  const [usuariosFiltrados, setUsuariosFiltrados] = useState<ResponseMinDto[]>([]);
  const [busca, setBusca] = useState("");
  const [usuariosFiltro, setUsuariosFiltro] = useState<string>("Estagiário");
  const [uriEdit, setUriEdit] = useState("/usuarios/estagiario/editar/");
  const [tableLabels, setTableLabes] = useState<string[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    if(!usuariosFiltrados) return;
    const token = localStorage.getItem('token');

    const rotas: Record<string, string> = {
      "Coordenador do curso":"/atores/tipo/Coordenador do curso",
      "Secretário":"/atores/tipo/Secretário",
      "Professor":"/atores/tipo/Professor",
      "Estagiário":"/estagiarios",
      "Advogado":"/advogados",
      "Assistido":"/assistidos"
    }

    const uris: Record<string, string> = {
      "Coordenador do curso":"/usuarios/editar/",
      "Secretário":"/usuarios/editar/",
      "Professor":"/usuarios/editar/",
      "Estagiário":"/usuarios/estagiario/editar/",
      "Advogado":"/usuarios/advogado/editar/",
      "Assistido":"/usuarios/assistido/editar/"
    }

    const tableHeaders: Record<string, string[]> = {
      "Estagiário": ["Nome", "Matrícula", "E-mail", "Telefone", "Estágio"],
      "default": ["Nome", "E-mail", "Registro"]
    }

    const rota = rotas[usuariosFiltro];
    if(!rota) {
      setUsuarios([]);
      setUsuariosFiltrados([]);
      return;
    }

    const uri = uris[usuariosFiltro];
    setUriEdit(uri);

    if(usuariosFiltro === "Estagiário") {
      setTableLabes(tableHeaders["Estagiário"]);
    } else {
      setTableLabes(tableHeaders["default"]);
    }
    
    const fecthUsuarios = async () => {
      try {
        const response = await axios.get(`${API_URL}${rota}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        
        const pages: Page<ResponseMinDto> = response.data;
        const dados = pages.content;
        setUsuarios(dados);
        setUsuariosFiltrados(dados);

      } catch(error) {
        console.log(error)
      }
    }
    fecthUsuarios();
  }, [usuariosFiltro]);

  useEffect(() => {
    let dados = [...usuarios];

    if (busca.trim() !== "" && usuariosFiltro !== "Estagiário") {
      dados = dados.filter(
        (usuario) =>
          usuario.nome.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.email.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.matricula.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.telefone.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.periodo.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.registro.toLowerCase().includes(busca.toLowerCase())
      );
    }

    setUsuariosFiltrados(dados);
  }, [busca, usuarios]);

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (
    <div className="min-vh-100 d-flex flex-column bg-light">
      
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
        <span className="navbar-brand fw-bold fs-4">Gerenciar Usuário</span>
        <button className="btn btn-outline-light ms-auto" onClick={() => navigate("/home/admin")}>
          ← Voltar
        </button>
      </nav>

      
      <div className="container my-5 flex-grow-1">
        <div className="d-flex flex-wrap justify-content-between align-items-center mb-4">
          
          <input
            type="text"
            className="form-control w-50 mb-2 mb-sm-0"
            placeholder="Buscar por nome ou email"
            value={busca}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setBusca(e.target.value)}
          />
          
          <select
            className="form-select w-auto"
            value={usuariosFiltro}
            onChange={(e) => setUsuariosFiltro(e.target.value)}
          >
            <option value="Estagiário">Estagiário</option>
            <option value="Coordenador do curso">Coordenador do curso</option>
            <option value="Secretário">Secretário</option>
            <option value="Professor">Professor</option>
            <option value="Advogado">Advogado</option>
            <option value="Assistido">Assistido</option>
          </select>
        </div>

        
        {usuariosFiltrados.length > 0 ? (
          <div className="table-responsive shadow-sm rounded">
            <table className="table table-hover align-middle bg-white rounded overflow-hidden">
              <thead className="table-dark">
                <tr>
                  {tableLabels.map((label) => (
                    <th>{label}</th>
                  ))}
                  <th className="text-center">Editar</th>
                </tr>
              </thead>
              <tbody>
                {usuariosFiltrados.map((usuario) => (
                  <tr key={usuario.id}>
                    {usuariosFiltro === "Estagiário" ? (
                      <>
                        <td>{usuario.nome}</td>
                        <td>{usuario.matricula}</td>
                        <td>{usuario.email}</td>
                        <td>{usuario.telefone}</td>
                        <td>{usuario.periodo}</td>
                      </>
                    ) : (
                      <>
                        <td>{usuario.nome}</td>
                        <td>{usuario.email}</td>
                        <td>{usuario.registro}</td>
                      </>
                    )}
                   <td className="text-center">
                        <button
                          className="btn btn-sm btn-outline-primary me-2"
                          onClick={() => navigate(`${uriEdit}${usuario.id}`)}
                        >
                          <EditIcon />
                        </button>
                      </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="alert alert-secondary text-center mt-5">
            Nenhum processo encontrado.
          </div>
        )}
      </div>

      
      <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
        © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
      </footer>
    </div>
  );
};
