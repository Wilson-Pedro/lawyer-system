import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams, Navigate } from "react-router-dom";
import { Table, Button, Form, Container, Row, Col } from "react-bootstrap";
import { PlusIcon, ArrowLeftIcon } from "../../Icons/Icon";
import "bootstrap/dist/css/bootstrap.min.css";
import Paginacao from "../../components/Paginacao/Paginacao";

const API_URL = process.env.REACT_APP_API;

interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

interface Movimento {
  id: number;
  numeroDoProcesso: string;
  advogado: string;
  movimento: string;
  registro: string;
}

export default function Movimento() {
  const [movimentos, setMovimentos] = useState<Movimento[]>([]);
  const [busca, setBusca] = useState("");

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const [primeiraPagina, setPrimeiraPagina] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  
  const [totalElements, setTotalElements] = useState(0);
  const [paginas, setPaginas] = useState<number[]>([]);
  const [ultimaPagina, setUltimaPagina] = useState<number>(10);
  const [paginaAtual, setPaginaAtual] = useState<number>(0);

  const [mostrarUltimaPagina, setMostrarUltimaPagina] = useState<boolean>(false);
  const [mostrarPrimeiraPagina, setMostrarPrimeiraPagina] = useState<boolean>(false);

  const navigate = useNavigate();
  const { numeroDoProcesso } = useParams<{ numeroDoProcesso: string }>();

  useEffect(() => {
    const token = localStorage.getItem('token');
    
    const movimentos = async () => {
      try {
        const response = await axios.get(`${API_URL}/movimentos/buscar/${numeroDoProcesso}?page=${page}&size=${size}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
        const pageData: Page<Movimento> = response.data;
        setMovimentos(pageData.content);
        setTotalPages(pageData.totalPages);
      } catch (error) {
        console.error("Erro ao buscar movimentos:", error);
      }
    };

    movimentos();
  }, [numeroDoProcesso, page, size]);

  const movimentosFiltrados = movimentos.filter(
    (movimento) =>
      movimento.movimento.toLowerCase().includes(busca.toLowerCase()) ||
      movimento.advogado.toLowerCase().includes(busca.toLowerCase()) ||
      movimento.numeroDoProcesso.toLocaleLowerCase().includes(busca.toLocaleLowerCase())
  );

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (
    <Container className="py-5">

      <div className="d-flex align-items-center justify-content-between mb-4">
        <div className="d-flex align-items-center gap-3">
          <Button
            variant="outline-secondary"
            className="d-flex align-items-center"
            onClick={() => navigate("/processos")}
          >
            <ArrowLeftIcon className="me-2" /> Voltar
          </Button>
          <h2 className="fw-bold text-dark mb-0">Movimentos do Processo</h2>
        </div>

        <Button
          variant="success"
          className="d-flex align-items-center shadow-sm"
          onClick={() => navigate(`/processos/${numeroDoProcesso}/movimento/cadastrar`)}
        >
          <PlusIcon className="me-2" /> Novo Movimento
        </Button>
      </div>

      <Row className="mb-4">
        <Col md={6}>
          <Form.Control
            type="text"
            placeholder="Buscar por movimento ou advogado..."
            value={busca}
            onChange={(e:any) => setBusca(e.target.value)}
            className="shadow-sm"
          />
        </Col>
      </Row>


      {movimentosFiltrados.length > 0 ? (
        <Table striped bordered hover responsive className="shadow-sm align-middle">
          <thead className="table-dark text-center">
            <tr>
              {/* <th>ID</th> */}
              <th>Nº Processo</th>
              <th>Movimento</th>
              <th>Advogado</th>
              <th>Registro</th>
              {/* <th>Ações</th> */}
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
    </Container>
  );
}
