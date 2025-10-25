import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import styles from './Movimento.module.css';
import { Table, Button, Form, Container, Row, Col } from "react-bootstrap";
import { EditIcon, PlusIcon, ArrowLeftIcon } from "../../Icons/Icon";
import "bootstrap/dist/css/bootstrap.min.css";

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
  const navigate = useNavigate();
  const { numeroDoProcesso } = useParams<{ numeroDoProcesso: string }>();

  useEffect(() => {
    const fetchMovimentos = async () => {
      try {
        const response = await axios.get(`${API_URL}/movimentos/buscar/${numeroDoProcesso}`);
        const pageData: Page<Movimento> = response.data;
        setMovimentos(pageData.content);
      } catch (error) {
        console.error("Erro ao buscar movimentos:", error);
      }
    };

    fetchMovimentos();
  }, [numeroDoProcesso]);

  const movimentosFiltrados = movimentos.filter(
    (item) =>
      item.movimento.toLowerCase().includes(busca.toLowerCase()) ||
      item.advogado.toLowerCase().includes(busca.toLowerCase())
  );

  return (
    <Container className="py-5">
      {/* Header */}
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
          onClick={() => navigate(`/cadastrarMovimento/${numeroDoProcesso}`)}
        >
          <PlusIcon className="me-2" /> Novo Movimento
        </Button>
      </div>

      {/* Área de Busca */}
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

      {/* Tabela de Movimentos */}
      {movimentosFiltrados.length > 0 ? (
        <Table striped bordered hover responsive className="shadow-sm align-middle">
          <thead className="table-dark text-center">
            <tr>
              {/* <th>ID</th> */}
              <th>Nº Processo</th>
              <th>Movimento</th>
              <th>Advogado</th>
              <th>Registro</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {movimentosFiltrados.map((item) => (
              <tr key={item.id}>
                {/* <td className="text-center">{item.id}</td> */}
                <td>{item.numeroDoProcesso}</td>
                <td>{item.movimento}</td>
                <td>{item.advogado}</td>
                <td className="text-center">{item.registro}</td>
                <td className="text-center">
                  <Button
                    variant="outline-primary"
                    size="sm"
                    className="me-2 d-inline-flex align-items-center"
                    onClick={() => navigate(`/processos/${item.numeroDoProcesso}/movimento`)}
                  >
                    <EditIcon />
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      ) : (
        <div className="text-center mt-5">
          <p className="text-muted fs-5">Nenhum movimento encontrado.</p>
        </div>
      )}
    </Container>
  );
}
