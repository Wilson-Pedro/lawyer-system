import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EditIcon, PlusIcon } from "../../Icons/Icon";

import Paginacao from "../../components/Paginacao/Paginacao";

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
    demandaStatusAluno: string;
    demandaStatusProfessor: string;
    prazo: string;
}

export default function Demandas() {
    const [demandas, setDemandas] = useState<Demanda[]>([]);
    const [filteredDemandas, setFilteredDemandas] = useState<Demanda[]>([]);
    const [busca, setBusca] = useState("");
    const [statusFiltro, setStatusFiltro] = useState<string>("Todos");
    const [primeiraPagina, setPrimeiraPagina] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(0);
    const [paginas, setPaginas] = useState<number[]>([]);
    const [size, setSize] = useState(10);
    const [ultimaPagina, setUltimaPagina] = useState<number>(10);
    const [paginaAtual, setPaginaAtual] = useState<number>(0);

    const [mostrarUltimaPagina, setMostrarUltimaPagina] = useState<boolean>(false);
    const [mostrarPrimeiraPagina, setMostrarPrimeiraPagina] = useState<boolean>(false);

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');

        const fetchDemandas = async () => {
            try {
                const response = await axios.get(`${API_URL}/demandas/status/${statusFiltro}?page=${page}&size=${size}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const pages: Page<Demanda> = response.data;
                setDemandas(pages.content);
                setTotalPages(pages.totalPages);
                setTotalElements(pages.totalElements);
                setFilteredDemandas(response.data);

            } catch (error) {
                console.error(error);
            }
        };
        fetchDemandas();
    }, [statusFiltro, page, size]);


    useEffect(() => {
        let dados = [...demandas]

        if (busca.trim() !== "") {
            dados = dados.filter(
                (d) =>
                    d.demanda.toLowerCase().includes(busca.toLowerCase()) ||
                    d.demandaStatusAluno.toLowerCase().includes(busca.toLowerCase()) ||
                    d.demandaStatusProfessor.toLowerCase().includes(busca.toLowerCase()) ||
                    d.estagiarioNome.toLowerCase().includes(busca.toLowerCase())
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
            case "Aguardando Professor":
                return "bg-warning text-center bg-opacity-25 text-warning fw-semibold";
            case "Prorrogada":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
            case "Dentro do Prazo":
                return "bg-success text-center bg-opacity-25 text-success fw-semibold";
            case "Fora do Prazo":
                return "bg-danger text-center bg-opacity-25 text-danger fw-semibold";
            case "Devolvido":
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
                <span className="navbar-brand fw-bold fs-4">Demandas - toalPages: {totalPages} - totalElements: {totalElements} - Page: {page} - Atual: {paginaAtual} - 
                    Primeira Página: {primeiraPagina} - Última Página: {ultimaPagina}
                </span>
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
                        <option value="Corrigido">Corrigido</option>
                        <option value="Em Correção">Em Correção</option>
                        <option value="Devolvido">Devolvido</option>
                        <option value="Dentro do Prazo">Dentro do Prazo</option>
                        <option value="Fora do Prazo">Fora do Prazo</option>
                        <option value="Aguardando Professor">Aguardando Professor</option>
                    </select>
                </div>


                {filteredDemandas.length > 0 ? (
                    <div className="table-responsive shadow-sm rounded">
                        <table className="table table-hover align-middle bg-white rounded overflow-hidden">
                            <thead className="table-dark">
                                <tr>
                                    <th>Demanda</th>
                                    <th>Estagiario</th>
                                    <th>Prazo</th>
                                    <th className="text-center">Status Aluno</th>
                                    <th className="text-center">Status Professor</th>
                                    <th className="text-center">Editar</th>
                                    <th className="text-center">Comentar</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredDemandas.map((demanda) => (
                                    <tr key={demanda.id}>
                                        <td>{demanda.demanda}</td>
                                        <td>{demanda.estagiarioNome}</td>
                                        <td>{demanda.prazo}</td>
                                        <td className={getStatusClass(demanda.demandaStatusAluno)}>
                                            {demanda.demandaStatusAluno}
                                        </td>
                                        <td className={getStatusClass(demanda.demandaStatusProfessor)}>
                                            {demanda.demandaStatusProfessor}
                                        </td>
                                        <td className="text-center">
                                            <button
                                                onClick={() => navigate(`/demandas/${demanda.id}/editar`)}
                                                className="btn btn-sm btn-outline-primary me-2"
                                            >
                                                <EditIcon />
                                            </button>
                                        </td>
                                        <td className="text-center">
                                            <button
                                                onClick={() => navigate(`/cadastrar/demanda/${demanda.id}/resposta`)}
                                                className="btn btn-sm btn-outline-primary me-2"
                                            >
                                                <PlusIcon />
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
