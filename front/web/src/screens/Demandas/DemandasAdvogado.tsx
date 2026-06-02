import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EditIcon, EyeIcon } from "../../Icons/Icon";
import Paginacao from "../../components/Paginacao/Paginacao";
import { PageableResponse } from "../../shared/types/PageableResponse";
import { DemandaAdvogado, DemandaProfessor } from "./types";

const API_URL = process.env.REACT_APP_API;

export default function DemandasAdvogado() {
    const [demandas, setDemandas] = useState<DemandaAdvogado[]>([]);
    const [filteredDemandas, setFilteredDemandas] = useState<DemandaAdvogado[]>([]);
    const [busca, setBusca] = useState("");
    const [statusFiltro, setStatusFiltro] = useState<string>("Todos");
    
    const [page, setPage] = useState(0);
    //const [size, setSize] = useState(10);

    const [primeiraPagina, setPrimeiraPagina] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    
    //const [totalElements, setTotalElements] = useState(0);
    const [paginas, setPaginas] = useState<number[]>([]);
    const [ultimaPagina, setUltimaPagina] = useState<number>(10);
    const [paginaAtual, setPaginaAtual] = useState<number>(0);

    const [mostrarUltimaPagina, setMostrarUltimaPagina] = useState<boolean>(false);
    const [mostrarPrimeiraPagina, setMostrarPrimeiraPagina] = useState<boolean>(false);
    const navigate = useNavigate();

    const params = useParams();
    const advogadoId = params.advogadoId || "";

    const size = 10;

    useEffect(() => {
        const token = localStorage.getItem('token');

        const fetchDemandas = async () => {
            try {
                const response = await axios.get(`${API_URL}/demandas/advogado/${advogadoId}?page=${page}&size=${size}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const pages: PageableResponse<DemandaProfessor> = response.data;
                setDemandas(pages.content);
                setFilteredDemandas(response.data);
                setTotalPages(pages.totalPages);
                //setTotalElements(pages.totalElements);
            } catch (error) {
                console.error(error);
            }
        };
        fetchDemandas();
    }, [statusFiltro, advogadoId, page, size]);

    useEffect(() => {
        let dados = [...demandas]

        if (busca.trim() !== "") {
            dados = dados.filter(
                (d) =>
                    d.demanda.toLowerCase().includes(busca.toLowerCase()) ||
                    d.demandaStatusAluno.toLowerCase().includes(busca.toLowerCase())
            );
        }

        setFilteredDemandas(dados);
    }, [busca, demandas]);

    const getStatusClass = (status: string): string => {
        switch (status) {
            case "Corrigido":
                return "bg-success text-center bg-opacity-25 text-success fw-semibold";
            case "Em Correção":
                return "bg-warning text-center bg-opacity-25 text-warning fw-semibold";
            case "Aguardando Aluno":
                return "bg-warning text-center bg-opacity-25 text-warning fw-semibold";
            case "Aguardando Professor":
                return "bg-warning text-center bg-opacity-25 text-warning fw-semibold";
            case "Aguardando Advogado":
                return "bg-warning text-center bg-opacity-25 text-warning fw-semibold";
            case "Prorrogada":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
            case "Dentro do Prazo":
                return "bg-success text-center bg-opacity-25 text-success fw-semibold";
            case "Fora do Prazo":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
            case "Devolvido":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
            case "Null":
                return "bg-secondary text-center bg-opacity-25 text-secondary fw-semibold";
            default:
                return "";
        }
    };

    const token = localStorage.getItem('token');
    if (!token) return <Navigate to="/login" />

    return (
        <div className="min-vh-100 d-flex flex-column bg-light">

            <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
                <span className="navbar-brand fw-bold fs-4">Demandas do Advogado</span>
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
                                    <th>Estagiario</th>
                                    <th>Professor</th>
                                    <th>Advogado</th>
                                    <th>Prazo</th>
                                    <th className="text-center">Status Aluno</th>
                                    <th className="text-center">Status Professor</th>
                                    {/* <th className="text-center">Status Advogado</th> */}
                                    <th className="text-center">Editar</th>
                                    <th className="text-center">Comentários</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredDemandas.map((demanda) => (
                                    <tr key={demanda.id}>
                                        <td>{demanda.demanda}</td>
                                        <td>{demanda.estagiarioNome}</td>
                                        <td>{demanda.professorNome}</td>
                                        <td>{demanda.advogadoNome}</td>
                                        <td>{demanda.prazo}</td>
                                        <td className={getStatusClass(demanda.demandaStatusAluno)}>
                                            {demanda.demandaStatusAluno}
                                        </td>
                                        <td className={getStatusClass(demanda.demandaStatusProfessor)}>
                                            {demanda.demandaStatusProfessor.toLocaleLowerCase() === "null" ? "Aguardando..." : demanda.demandaStatusProfessor}
                                        </td>
                                        {/* <td className={getStatusClass(demanda.demandaStatusAdvogado)}>
                                            {demanda.demandaStatusAdvogado.toLocaleLowerCase() === "null" ? "Aguardando..." : demanda.demandaStatusAdvogado}
                                        </td> */}
                                        <td className="text-center">
                                            <button
                                                onClick={() => navigate(`/demandas/advogado/${advogadoId}/demanda/${demanda.id}/editar`)}
                                                className="btn btn-sm btn-outline-primary me-2"
                                            >
                                                <EditIcon />
                                            </button>
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

            <Paginacao 
                page={page}
                totalPages={totalPages}
                paginaAtual={paginaAtual}
                primeiraPagina={primeiraPagina}
                ultimaPagina={ultimaPagina}
                paginas={paginas}
                setPaginaAtual={setPaginaAtual}
                setPrimeiraPagina={setPrimeiraPagina}
                setUltimaPagina={setUltimaPagina}
                setPage={setPage}
                mostrarPrimeiraPagina={mostrarPrimeiraPagina}
                mostrarUltimaPagina={mostrarUltimaPagina}
                setMostrarUltimaPagina={setMostrarUltimaPagina}
                setMostrarPrimeiraPagina={setMostrarPrimeiraPagina}
                setPaginas={setPaginas}
            />

            <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
                © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
            </footer>
        </div>
    );
};
