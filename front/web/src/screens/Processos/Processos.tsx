import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EditIcon, FileAltIcon } from "../../Icons/Icon";

const API_URL = process.env.REACT_APP_API;

interface Processo {
  id: string;
  numeroDoProcesso: string;
  assunto: string;
  prazoFinal: string;
  responsavel: string;
  advogadoNome: string;
  statusDoProcesso: "Tramitando" | "Suspenso" | "Arquivado";
}

export default function Processos() {
  const [processos, setProcessos] = useState<Processo[]>([]);
  const [filteredProcessos, setFilteredProcessos] = useState<Processo[]>([]);
  const [busca, setBusca] = useState("");
  const [statusFiltro, setStatusFiltro] = useState<string>("Todos");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProcessos = async () => {
      try {
        const response = await axios.get(`${API_URL}/processos/statusDoProcesso/${statusFiltro}`);
        setProcessos(response.data);
        setFilteredProcessos(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchProcessos();
  }, [statusFiltro]);

  useEffect(() => {
    let dados = [...processos];

    if (busca.trim() !== "") {
      dados = dados.filter(
        (p) =>
          p.numeroDoProcesso.toLowerCase().includes(busca.toLowerCase()) ||
          p.assunto.toLowerCase().includes(busca.toLowerCase()) ||
          p.advogadoNome.toLowerCase().includes(busca.toLowerCase())
      );
    }

    setFilteredProcessos(dados);
  }, [busca, processos]);

  const getStatusClass = (status: string): string => {
    switch (status) {
      case "Tramitando":
        return "bg-success bg-opacity-25 text-success fw-semibold";
      case "Suspenso":
        return "bg-warning bg-opacity-25 text-warning fw-semibold";
      case "Arquivado":
        return "bg-danger bg-opacity-25 text-danger fw-semibold";
      default:
        return "";
    }
  };

  return (
    <div className="min-vh-100 d-flex flex-column bg-light">
      
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
        <span className="navbar-brand fw-bold fs-4">Lista de Processos</span>
        <button className="btn btn-outline-light ms-auto" onClick={() => navigate("/admin")}>
          ← Voltar
        </button>
      </nav>

      
      <div className="container my-5 flex-grow-1">
        <div className="d-flex flex-wrap justify-content-between align-items-center mb-4">
          
          <input
            type="text"
            className="form-control w-50 mb-2 mb-sm-0"
            placeholder="Buscar por número, assunto ou advogado..."
            value={busca}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setBusca(e.target.value)}
          />

          
          {statusFiltro}
          <select
            className="form-select w-auto"
            value={statusFiltro}
            onChange={(e) => setStatusFiltro(e.target.value)}
          >
            <option value="Todos">Todos</option>
            <option value="Tramitando">Tramitando</option>
            <option value="Suspenso">Suspenso</option>
            <option value="Arquivado">Arquivado</option>
          </select>
        </div>

        
        {filteredProcessos.length > 0 ? (
          <div className="table-responsive shadow-sm rounded">
            <table className="table table-hover align-middle bg-white rounded overflow-hidden">
              <thead className="table-dark">
                <tr>
                  <th>Nº Processo</th>
                  <th>Assunto</th>
                  <th>Prazo Final</th>
                  <th>Responsável</th>
                  <th>Advogado</th>
                  <th>Status</th>
                  <th className="text-center">Ações</th>
                </tr>
              </thead>
              <tbody>
                {filteredProcessos.map((proc) => (
                  <tr key={proc.id}>
                    <td>{proc.numeroDoProcesso}</td>
                    <td>{proc.assunto}</td>
                    <td>{proc.prazoFinal}</td>
                    <td>{proc.responsavel}</td>
                    <td>{proc.advogadoNome}</td>
                    <td className={getStatusClass(proc.statusDoProcesso)}>
                      {proc.statusDoProcesso}
                    </td>
                    <td className="text-center">
                      <button
                        className="btn btn-sm btn-outline-primary me-2"
                        onClick={() => navigate(`/processos/editar/${proc.id}`)}
                      >
                        <EditIcon />
                      </button>
                      <button
                        className="btn btn-sm btn-outline-success"
                        onClick={() => navigate(`/processos/${proc.numeroDoProcesso}/movimento`)}
                      >
                        <FileAltIcon />
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
