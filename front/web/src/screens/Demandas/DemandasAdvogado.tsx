import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EyeIcon, PlusIcon } from "../../Icons/Icon";

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
    tempestividade:string;
}

export default function DemandasAdvogado() {
    const [demandas, setDemandas] = useState<Demanda[]>([]);
    const [filteredDemandas, setFilteredDemandas] = useState<Demanda[]>([]);
    const [busca, setBusca] = useState("");
    const [statusFiltro, setStatusFiltro] = useState<string>("Todos");
    const navigate = useNavigate();

    const params = useParams();
    const advogadoId = params.advogadoId || "";

    useEffect(() => {
        const token = localStorage.getItem('token');

        const fetchDemandas = async () => {
            try {
                const response = await axios.get(`${API_URL}/demandas/advogado/${advogadoId}?page=0&size=20`, {
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
    }, [statusFiltro, advogadoId]);

    useEffect(() => {
        let dados = [...demandas]

        if (busca.trim() !== "") {
            dados = dados.filter(
                (d) =>
                    d.demanda.toLowerCase().includes(busca.toLowerCase()) ||
                    d.demandaStatus.toLowerCase().includes(busca.toLowerCase())
            );
        }

        setFilteredDemandas(dados);
    }, [busca, demandas]);

    const getStatusClass = (status: string): string => {
        switch (status) {
            case "Devolvido":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
            case "Em Correção":
                return "bg-warning text-center bg-opacity-25 text-warning fw-semibold";
            case "Corrigido":
                return "bg-success text-center bg-opacity-25 text-success fw-semibold";
            case "Dentro do Prazo":
                return "bg-success text-center bg-opacity-25 text-success fw-semibold";
            case "Fora do Prazo":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
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
                <button className="btn btn-outline-light ms-auto" onClick={() => navigate("/home/advogado")}>
                    ← Voltar
                </button>
            </nav>


            <div className="container my-5 flex-grow-1">

                {filteredDemandas.length > 0 ? (
                    <div className="table-responsive shadow-sm rounded">
                        <table className="table table-hover align-middle bg-white rounded overflow-hidden">
                            <thead className="table-dark">
                                <tr>
                                    <th>Demanda</th>
                                    <th>Prazo</th>
                                    <th className="text-center">Status</th>
                                    <th className="text-center">Tempestividade</th>
                                    <th className="text-center">Comentários</th>
                                    {/* <th className="text-center">Responder</th> */}
                                </tr>
                            </thead>
                            <tbody>
                                {filteredDemandas.map((demanda) => (
                                    <tr key={demanda.id}>
                                        <td>{demanda.demanda}</td>
                                        <td>{demanda.prazo}</td>
                                        <td className={getStatusClass(demanda.demandaStatus)}>
                                            {demanda.demandaStatus}
                                        </td>
                                        <td className={getStatusClass(demanda.tempestividade)}>
                                            {demanda.tempestividade}
                                            </td>
                                        <td className="text-center">
                                            <button
                                            onClick={() => navigate(`/demandas/${demanda.id}/respostas`)}
                                                className="btn btn-sm btn-outline-primary"
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
