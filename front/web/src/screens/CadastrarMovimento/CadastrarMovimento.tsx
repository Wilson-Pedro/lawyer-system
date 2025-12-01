import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams, Navigate } from "react-router-dom";
import { Container, Form, Button, Row, Col, Toast, ToastContainer, Card } from "react-bootstrap";
import { ArrowLeftIcon, SaveIcon } from "../../Icons/Icon";
import "bootstrap/dist/css/bootstrap.min.css";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarMovimento() {
  const navigate = useNavigate();

  const [numeroDoProcesso, setNumeroDoProcesso] = useState("");
  const [processoId, setProcessoId] = useState<number>(0);
  const [nomeAdvogado, setNomeAdvogado] = useState("");
  const [advogadoId, setAdvogadoId] = useState<number>(0);
  const [movimento, setMovimento] = useState("");

  const [mostrarToast, setMostrarToast] = useState<boolean>(false);
  const [mensagemToast, setMesagemToast] = useState("");
  const [toastVariante, setToastVariant] = useState<"success" | "danger">("success");

  const params = useParams();
  const numeroDoProcessoParams = params.numeroDoProcesso || "";

  // Buscar processo por número do processo
  useEffect(() => {
    const buscarProcessoPorNumeroDoProcesso = async () => {
      try {
        const response = await axios.get(
          `${API_URL}/processos/numeroDoProcesso/${numeroDoProcessoParams}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );
        setNumeroDoProcesso(response.data.numeroDoProcesso);
        setProcessoId(response.data.id);
        setNomeAdvogado(response.data.advogadoNome);
        setAdvogadoId(response.data.advogadoId);
      } catch (error) {
        console.error("Erro ao buscar processo pelo número do processo", error);
      }
    };

    buscarProcessoPorNumeroDoProcesso();
  }, [numeroDoProcessoParams]);

  const cadastrarMovimento = async (e: React.FormEvent) => {
    e.preventDefault();
    //const token = localStorage.getItem('token');
    try {
      await axios.post(`${API_URL}/movimentos/`, {
        processoId,
        advogadoId,
        movimento,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      setMesagemToast("Movimento cadastrado com sucesso.");
      setToastVariant("success");
      setMostrarToast(true);
      limparCampos();
    } catch (error) {
      console.error(error);
      alert("Erro ao cadastrar movimento.");
    }
  };

  const limparCampos = () => {
    setMovimento("");
  };

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (
    <Container className="py-5">
      
      <div className="d-flex align-items-center justify-content-between mb-4">
        <Button
          variant="outline-secondary"
          className="d-flex align-items-center"
          onClick={() =>
            navigate(-1)
          }
        >
          <ArrowLeftIcon className="me-2" /> Voltar
        </Button>

        <h2 className="fw-bold text-dark mb-0">Cadastro de Movimento</h2>
      </div>

     
      <Card className="shadow-sm p-4 border-0">
        <Form onSubmit={cadastrarMovimento}>
          <Row className="mb-3">
            <Col md={6}>
              <Form.Group controlId="numeroProcesso">
                <Form.Label className="fw-semibold">Nº do Processo</Form.Label>
                <Form.Control
                  type="text"
                  value={numeroDoProcesso}
                  disabled
                  readOnly
                  className="bg-light"
                />
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group controlId="advogado">
                <Form.Label className="fw-semibold">Advogado</Form.Label>
                <Form.Control
                  type="text"
                  value={nomeAdvogado}
                  disabled
                  readOnly
                  className="bg-light"
                />
              </Form.Group>
            </Col>
          </Row>

          <Row>
            <Col md={12}>
              <Form.Group controlId="movimento">
                <Form.Label className="fw-semibold">Movimento</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Digite o movimento realizado..."
                  value={movimento}
                  onChange={(e) => setMovimento(e.target.value)}
                  required
                />
              </Form.Group>
            </Col>
          </Row>

          <div className="d-flex justify-content-end mt-4">
            <Button
              type="submit"
              variant="primary"
              size="lg"
              className="d-flex align-items-center px-4 shadow-sm"
            >
              <SaveIcon className="me-2" /> Cadastrar Movimento
            </Button>
          </div>
        </Form>
      </Card>

      <ToastContainer
        position="top-end"
        className="p-3"
        style={{ zIndex: 9999 }}
      >
        <Toast
          onClose={() => setMostrarToast(false)}
          show={mostrarToast}
          bg={toastVariante}
          delay={3000}
          autohide
        >
          <Toast.Body className="text-white fw-semibold">
            {mensagemToast}
          </Toast.Body>
        </Toast>
      </ToastContainer>
    </Container>
  );
}
