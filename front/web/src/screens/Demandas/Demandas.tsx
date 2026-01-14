import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { EditIcon, PlusIcon } from "../../Icons/Icon";

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
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(0);
    const [pages, setPages] = useState<number[]>([]);
    const [size, setSize] = useState(1);
    const [paginaAtual, setPaginaAtual] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        definirPaginacao();
    }, [pages]);

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
                definirPaginacao();
                setFilteredDemandas(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchDemandas();
    }, [statusFiltro, page]);

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

    const paginaAnterior = () => {
        setPaginaAtual(page - 1);
        setPage(paginaAtual - 1);
    }

    const proximaPagina = () => {
        setPaginaAtual(page + 1);
        setPage(paginaAtual + 1);
    }

    const navegarParaPagina = (pagina:number) => {
        if(pagina >=0 && pagina <= totalPages) {
            setPage(pagina);
            setPaginaAtual(pagina);
        }
    }

    const definirPaginacao = () => {
        let pagina = 0;
        let paginas: number[] = [];
        for(let i = 0; i < totalPages; i++) {
            paginas.push(pagina);
            pagina++;
        }
        setPages(paginas);
    }

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
                <span className="navbar-brand fw-bold fs-4">Demandas - toalPages: {totalPages} - totalElements: {totalElements} - Page: {page} - Atual: {paginaAtual}</span>
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
                                    {/* <th className="text-center">Responder</th> */}
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

            <nav aria-label="Page navigation example">
                <ul className="pagination justify-content-center">
                    {page !== 0 ? (
                        <li className="page-item">
                            <button className="page-link" onClick={() => paginaAnterior()}>Anterior</button>
                        </li>
                    ) : (
                        <li className="page-item">
                            <button className="page-link disabled">Anterior</button>
                        </li>
                    )}

                    {pages.map((item, index) => (

                        <>
                            {page === item ? (
                                <li className="page-item">
                                    <button className="page-link active" onClick={() => navegarParaPagina(item)}>{item + 1}</button>
                                </li>
                            ) : (
                                <li className="page-item">
                                    <button className="page-link" onClick={() => navegarParaPagina(item)}>{item + 1}</button>
                                </li>
                            )}
                        </>

                    ))}

                    {page !== (totalPages - 1) ? (
                        <li className="page-item">
                            <button className="page-link" onClick={() => proximaPagina()}>Próximo</button>
                        </li>
                    ) : (
                        <li className="page-item">
                            <button className="page-link disabled">Próximo</button>
                        </li>
                    )}
                </ul>
            </nav>


            <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
                © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
            </footer>
        </div>
    );
};
