import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import { Table, Button, Form, Row, Col } from "react-bootstrap";
import { PlusIcon } from "../../Icons/Icon";
import "bootstrap/dist/css/bootstrap.min.css";
import styles from './MovimentarProcesso.module.css';

const API_URL = process.env.REACT_APP_API;

export interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

interface Processo {
    id: number;
    numeroDoProcesso: string;
    assunto: string;
    prazoFinal: string;
    responsavel: string;
    advogadoNome: string;
    statusDoProcesso: "Tramitando" | "Suspenso" | "Arquivado";
}

interface Movimento {
    id: number;
    numeroDoProcesso: string;
    advogado: string;
    movimento: string;
    registro: string;
}

export default function MovimentarProcesso() {

    const [numeroDoProcesso, setNumeroDoProcesso] = useState("");
    const [numeroDoProcessoSearch, setNumeroDoProcessoSearch] = useState("");
    const [busca, setBusca] = useState("");
    const [processos, setProcessos] = useState<Processo[]>([]);
    const [movimentos, setMovimentos] = useState<Movimento[]>([]);
    
    const [disabled, setDisabled] = useState(true);

    const navigate = useNavigate();

    const page = 0;
    const size = 20;

    useEffect(() => {
        const buscarProcessos = async () => {
            if (numeroDoProcessoSearch.trim.length < 2) {
                setProcessos([]);
            }

            try {
                const response = await axios.get(`${API_URL}/processos/buscar/${numeroDoProcessoSearch}?page=${page}&size=${size}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                    }
                });
                const pageData: Page<Processo> = response.data;
                setProcessos(pageData.content);

            } catch (error) {
                console.log(error);
            }

        }

        const delay = setTimeout(buscarProcessos, 100);

        return () => clearTimeout(delay);
    }, [numeroDoProcessoSearch]);

    useEffect(() => {

        const movimentos = async () => {
            try {
                const response = await axios.get(`${API_URL}/movimentos/buscar/${numeroDoProcesso}`, {
                    headers: {
                    Authorization: `Bearer ${token}`
                    }
                });

                const pageData: Page<Movimento> = response.data;
                setMovimentos(pageData.content);
                setDisabled(false);
            } catch (error) {
                console.error("Erro ao buscar movimentos:", error);
            }
        };
        movimentos();
    }, [numeroDoProcesso]);


    const setProcessoSearch = (processo: Processo) => {
        setNumeroDoProcesso(processo.numeroDoProcesso);
        setNumeroDoProcessoSearch("");
    }

    const setProcesso = (numeroDoProcesso: string) => {
        setNumeroDoProcesso(numeroDoProcesso);
        setNumeroDoProcessoSearch(numeroDoProcesso);
        if(numeroDoProcesso.length === 0) {
            setProcessos([]);
        }
    }

    const movimentosFiltrados = movimentos.filter(
        (movimento) =>
            movimento.movimento.toLocaleLowerCase().includes(busca.toLocaleLowerCase()) ||
            movimento.advogado.toLowerCase().includes(busca.toLocaleLowerCase())
    );

    const token = localStorage.getItem('token');
    if(!token) return <Navigate to="/login" />

    return (
        <div className="min-vh-100 d-flex flex-column bg-light">

            <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
                <span className="navbar-brand fw-bold fs-4">Movimentar Processo</span>
                <button className="btn btn-outline-light ms-auto" onClick={() => navigate("/home/admin")}>
                    ← Voltar
                </button>
            </nav>


            <div className="container my-5 flex-grow-1">
                <div className={styles.inputGroup}>
                    <label className="fw-bold text-dark mb-1">Nº do Processo </label>
                    <input
                        type="text"
                        className="form-control w-50 mb-2 mb-sm-0"
                        placeholder="Digite o número do processo"
                        value={numeroDoProcessoSearch || numeroDoProcesso}
                        onChange={(e) => setProcesso(e.target.value)}
                    />
                    {processos.length > 0 && (
                        <ul className={styles.ul}>
                            {processos.map((data) => (
                                <li
                                    className={styles.li}
                                    key={data.id}
                                    onClick={() => {
                                        setProcessoSearch(data)
                                        setDisabled(false)
                                    }}
                                >
                                    {data.numeroDoProcesso}
                                </li>
                            ))} 
                        </ul>
                    )}

                </div>

                <br /><br />

                        <div className="d-flex align-items-center justify-content-between mb-4">
                            <div className="d-flex align-items-center gap-3">

                                <h2 className="fw-bold text-dark mb-0">Movimentos do Processo {numeroDoProcesso || ''}</h2>
                            </div>

                            <Button
                                variant="success"
                                className="d-flex align-items-center shadow-sm"
                                onClick={() => navigate(`/processos/${numeroDoProcesso}/movimento/cadastrar`)}
                                disabled={disabled}
                            >
                                <PlusIcon className="me-2" /> Novo Movimento
                            </Button>
                        </div>
                {movimentos.length > 0 ? (
                    <>
                        <Row className="mb-4">
                            <Col md={6}>
                                <Form.Control
                                    type="text"
                                    placeholder="Buscar por movimento ou advogado..."
                                    value={busca}
                                    onChange={(e: any) => setBusca(e.target.value)}
                                    className="shadow-sm"
                                />
                            </Col>
                        </Row>

                        {movimentosFiltrados.length > 0 ? (
                            <Table striped bordered hover responsive className="shadow-sm align-middle">
                                <thead className="table-dark text-center">
                                    <tr>
                                        <th>Nº Processo</th>
                                        <th>Movimento</th>
                                        <th>Advogado</th>
                                        <th>Registro</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {movimentosFiltrados.map((item) => (
                                        <tr key={item.id}>

                                            <td>{item.numeroDoProcesso}</td>
                                            <td>{item.movimento}</td>
                                            <td>{item.advogado}</td>
                                            <td className="text-center">{item.registro}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                        ) : (
                            <div className="alert alert-secondary text-center mt-5">
                                <p className="text-muted fs-5">Nenhum movimento encontrado.</p>
                            </div>
                        )}
                    </>
                ) : (
                    <div className="alert alert-secondary text-center mt-5">
                        <p className="text-muted fs-5">Nenhum movimento encontrado.</p>
                    </div>
                )}

            </div>


            <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
                © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD - Estácio.
            </footer>
        </div>
    );
};
