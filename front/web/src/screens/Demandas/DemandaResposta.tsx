import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const API_URL = process.env.REACT_APP_API;

interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

interface DemandaResposta {
    id: number;
    demandaId: string;
    estagiarioId: string;
    estagiarioNome: string; 
    resposta: string;
    respondidoPor: string;
    registro:string;
}

export default function DemandaResposta() {
    const [demandaRespostas, setDemandaRespostas] = useState<DemandaResposta[]>([]);
    const navigate = useNavigate();

    const params = useParams();
    const demandaId = params.demandaId || '';

    useEffect(() => {
        const token = localStorage.getItem('token');

        const fetchDemandasResposta = async () => {
            try {
                const response = await axios.get(`${API_URL}/demandas/responde/demanda/${demandaId}?page=0&size=20`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const page: Page<DemandaResposta> = response.data;
                setDemandaRespostas(page.content);
            } catch (error) {
                console.error(error);
            }
        };
        fetchDemandasResposta();
    }, [demandaId]);

    const token = localStorage.getItem('token');
    if (!token) return <Navigate to="/login" />

    return (
        <div className="min-vh-100 d-flex flex-column bg-light">

            <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
                <span className="navbar-brand fw-bold fs-4">Respostas</span>
                <button className="btn btn-outline-light ms-auto" onClick={() => navigate(-1)}>
                    ← Voltar
                </button>
            </nav>


            <div className="container my-5 flex-grow-1">

                {demandaRespostas.length > 0 ? (
                    <div className="table-responsive shadow-sm rounded">
                        <table className="table table-hover align-middle bg-white rounded overflow-hidden">
                            <thead className="table-dark">
                                <tr>
                                    <th>Resposta</th>
                                    <th>Respondido Por</th>
                                    <th>Quem respondeu</th>
                                    <th>Respondido em</th>
                                </tr>
                            </thead>
                            <tbody>
                                {demandaRespostas.map((dr) => (
                                    <tr key={dr.id}>
                                        <td>{dr.resposta}</td>
                                        <td>{dr.respondidoPor}</td>
                                        <td>{dr.estagiarioNome}</td>
                                        <td>{dr.registro}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ) : (
                    <div className="alert alert-secondary text-center mt-5">
                        Nenhuma resposta encontrada.
                    </div>
                )}
            </div>


            <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
                © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
            </footer>
        </div>
    );
};
