import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EyeIcon } from "../../Icons/Icon";

const API_URL = process.env.REACT_APP_API;

interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

interface Demanda {
    id: number;
    demanda: string;
    estagiarioNome: string;
    estagiarioId: string;
    demandaStatus: string;
    prazo: string;
}

export default function Demandas() {
    const [demandas, setDemandas] = useState<Demanda[]>([]);
    const [filteredDemandas, setFilteredDemandas] = useState<Demanda[]>([]);
    const [busca, setBusca] = useState("");
    const [statusFiltro, setStatusFiltro] = useState<string>("Todos");
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');

        const fetchDemandas = async () => {
            try {
                const response = await axios.get(`${API_URL}/demandas/status/${statusFiltro}?page=0&size=20`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const page: Page<Demanda> = response.data;
                setDemandas(page.content);
                setFilteredDemandas(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchDemandas();
    }, [statusFiltro]);

    useEffect(() => {
        let dados = [...demandas]

        if (busca.trim() !== "") {
            dados = dados.filter(
                (d) =>
                    d.demanda.toLowerCase().includes(busca.toLowerCase()) ||
                    d.demandaStatus.toLowerCase().includes(busca.toLowerCase()) ||
                    d.estagiarioNome.toLowerCase().includes(busca.toLowerCase())
            );
        }

        setFilteredDemandas(dados);
    }, [busca, demandas]);

    const getStatusClass = (status: string): string => {
        switch (status) {
            case "Atendido":
                return "bg-success bg-opacity-25 text-success fw-semibold";
            case "Não Atendido":
                return "bg-warning bg-opacity-25 text-warning fw-semibold";
            case "Prorrogada":
                return "bg-danger bg-opacity-25 text-danger fw-semibold";
            default:
                return "";
        }
    };

    const token = localStorage.getItem('token');
    if (!token) return <Navigate to="/login" />

    return (
        <div className="min-vh-100 d-flex flex-column bg-light">

            <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
                <span className="navbar-brand fw-bold fs-4">Demandas</span>
                <button className="btn btn-outline-light ms-auto" onClick={() => navigate("/home/admin")}>
                    ← Voltar
                </button>
            </nav>


            <div className="container my-5 flex-grow-1">
                <div className="d-flex flex-wrap justify-content-between align-items-center mb-4">

                    <input
                        type="text"
                        className="form-control w-50 mb-2 mb-sm-0"
                        placeholder="Buscar por demanda, estgiário, status..."
                        value={busca}
                        onChange={(e: ChangeEvent<HTMLInputElement>) => setBusca(e.target.value)}
                    />

                    <select
                        className="form-select w-auto"
                        value={statusFiltro}
                        onChange={(e) => setStatusFiltro(e.target.value)}
                    >
                        <option value="todos">Todos</option>
                        <option value="Atendido">Atendido</option>
                        <option value="Não Atendido">Não Atendido</option>
                        <option value="Prorrogada">Prorrogada</option>
                    </select>
                </div>


                {filteredDemandas.length > 0 ? (
                    <div className="table-responsive shadow-sm rounded">
                        <table className="table table-hover align-middle bg-white rounded overflow-hidden">
                            <thead className="table-dark">
                                <tr>
                                    <th>Demanda</th>
                                    <th>Estagiario</th>
                                    <th>Status</th>
                                    <th>Prazo</th>
                                    <th className="text-center">Ver</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredDemandas.map((demanda) => (
                                    <tr key={demanda.id}>
                                        <td>{demanda.demanda}</td>
                                        <td>{demanda.estagiarioNome}</td>
                                        <td>{demanda.prazo}</td>
                                        <td className={getStatusClass(demanda.demandaStatus)}>
                                            {demanda.demandaStatus}
                                        </td>
                                        <td className="text-center">
                                            <button
                                            onClick={() => navigate(`/demandas/${demanda.id}/respostas`)}
                                                className="btn btn-sm btn-outline-primary me-2"
                                            >
                                                <EyeIcon />
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ) : (
                    <div className="alert alert-secondary text-center mt-5">
                        Nenhuma Demanda encontrada.
                    </div>
                )}
            </div>


            <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
                © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
            </footer>
        </div>
    );
};
