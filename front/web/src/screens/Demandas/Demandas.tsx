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
    const [primeiraPagina, setPrimeiraPagina] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(0);
    const [paginas, setPaginas] = useState<number[]>([]);
    const [size, setSize] = useState(1);
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

    useEffect(() => {
        definirPaginacao();
    }, [paginaAtual, totalPages, ultimaPagina]);

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
        let pagina = primeiraPagina;
        let paginas: number[] = [];

        if(paginaAtual + 1 === 1) {
            console.log(`paginaAtual + 1 === 1`)
            setPrimeiraPagina(0);
            setUltimaPagina(totalPages <= 11 ? totalPages : 12);
            setMostrarUltimaPagina(true);
            setMostrarPrimeiraPagina(false);
        }


        if(totalPages <= 12) {
            console.log(`totalPages <= 12`)
            setUltimaPagina(totalPages)
            setPrimeiraPagina(0)
            setMostrarPrimeiraPagina(false);
            setMostrarUltimaPagina(false);
        }


        if(paginaAtual === primeiraPagina) {

            console.log(`paginaAtual === primeiraPagina`)

            if(paginaAtual - 10 > 10) {
                console.log(`primeiraPagina: 1`)
                setUltimaPagina(paginaAtual + 2);
                setPrimeiraPagina(paginaAtual - 11);
                setMostrarPrimeiraPagina(true);
                setMostrarUltimaPagina(true);

            } else if(paginaAtual > 12) {
                console.log(`primeiraPagina: 2`)
                setPrimeiraPagina(paginaAtual - 10);
                setUltimaPagina(paginaAtual + 2);
                setMostrarUltimaPagina(totalPages <= 12 ? false : true);
                setMostrarPrimeiraPagina(true);

            } else if(paginaAtual - 10 < 12) {
                console.log(`primeiraPagina: 3`)
                setPrimeiraPagina(0);
                setUltimaPagina(totalPages <= 12 ? totalPages : 12);
                setMostrarUltimaPagina(totalPages <= 12 ? false : true);
                setMostrarPrimeiraPagina(false);
            }

        } else if (paginaAtual + 1 === ultimaPagina) {

            console.log(`paginaAtual + 1 === ultimaPagina`)

            if((paginaAtual + 1 === ultimaPagina) && paginaAtual + 1 !== totalPages) {
                console.log(`ultimaPagina: 1`)
                setMostrarUltimaPagina(true);
                setMostrarPrimeiraPagina(true);
            }

            if((totalPages - 1) - ultimaPagina > 10) {
                console.log(`ultimaPagina: 2`)
                setMostrarUltimaPagina(true);
                setPrimeiraPagina(paginaAtual - 1);
                setUltimaPagina(paginaAtual + 10);

            } else if((totalPages - 1) - ultimaPagina <= 10) {
                console.log(`ultimaPagina: 3`)
                setMostrarUltimaPagina(false);
                setMostrarPrimeiraPagina(totalPages - 13 > 0 ? true : false);
                setPrimeiraPagina(totalPages - 13 > 0 ? totalPages - 13 : 0);
                setUltimaPagina(totalPages);

            } else if (paginaAtual + 1 === totalPages) {
                console.log(`ultimaPagina: 4`)
                setPrimeiraPagina(totalPages - 10);
                setUltimaPagina(totalPages);
                setMostrarUltimaPagina(false);
                setMostrarPrimeiraPagina(true);
            }

        } else if (paginaAtual + 1 === totalPages) {
            console.log(`paginaAtual + 1 === totalPages`)
                console.log(`paginal Atual === pagina total`)
                setPrimeiraPagina(totalPages - 13);
                setUltimaPagina(totalPages);
                setMostrarUltimaPagina(false);
                setMostrarPrimeiraPagina(true);
        }


        for(let i = primeiraPagina; i < ultimaPagina; i++) {
            paginas.push(pagina);
            pagina++;
        }
        setPaginas(paginas);
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
                    <li className="page-item">
                        <button 
                        className={`page-link ${page === 0 ? "disabled" : ""}`} 
                        onClick={page === 0 ? undefined : paginaAnterior}
                        disabled={page === 0}
                        >Anterior</button>
                    </li>

                    {totalPages <= 11 ? (
                        <>

                            {paginas.map((item, index) => (

                                <li className="page-item" key={index}>
                                    <button 
                                    className={`page-link ${page === item ? "active" : ""}`} 
                                    onClick={() => navegarParaPagina(item)}
                                    >{page === item ? paginaAtual + 1 : item + 1}</button>
                                </li>

                            ))}
                        </>

                    ) : (
                        <>

                            {mostrarPrimeiraPagina && (
                                <>
                                    <li className="page-item">
                                        <button 
                                        className={`page-link`}
                                        onClick={() => navegarParaPagina(0)}
                                        >1</button>
                                    </li>
                                    <li className="page-item">
                                        <button className="page-link disabled">...</button>
                                    </li>
                                </>
                            )}

                            {paginas.map((item, index) => (

                                <li className="page-item" key={index}>
                                    <button 
                                    className={`page-link ${page === item ? "active" : ""}`} 
                                    onClick={() => navegarParaPagina(item)}>{item + 1}</button>
                                </li>

                            ))}

                            {mostrarUltimaPagina && (
                                <>
                                    <li className="page-item">
                                        <button className="page-link disabled">...</button>
                                    </li>
                                    <li className="page-item">
                                        <button 
                                        className={`page-link ${page === totalPages -1 ? "active" : ""}`}
                                        onClick={() => navegarParaPagina(totalPages - 1)}
                                        >{totalPages}</button>
                                    </li>
                                </>
                            )}

                        </>
                    )} 

                    <li className="page-item">
                        <button 
                        className={`page-link ${page === totalPages - 1 ? "disabled" : ""}`} 
                        onClick={page === (totalPages - 1) ? undefined : proximaPagina}
                        disabled={page === (totalPages - 1)}
                        >Próximo</button>
                    </li>

                </ul>
            </nav>


            <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
                © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
            </footer>
        </div>
    );
};
