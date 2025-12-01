import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams, Navigate } from "react-router-dom";
import { Container, Form, Button, Row, Col, Toast, ToastContainer, Card } from "react-bootstrap";
import { ArrowLeftIcon, SaveIcon } from "../../Icons/Icon";
import "bootstrap/dist/css/bootstrap.min.css";

const API_URL = process.env.REACT_APP_API;

interface Response {
  id: number;
  nome: string;
}

export default function CadastrarDemandaResposta() {
  const navigate = useNavigate();


  const [estagiarioNome, setEstagiarioNome] = useState("");
  const [estagiarioId, setEstagiarioId] = useState<number>(0);
  const [resposta, setResposta] = useState("");

  const [mostrarToast, setMostrarToast] = useState<boolean>(false);
  const [mensagemToast, setMesagemToast] = useState("");
  const [toastVariante, setToastVariant] = useState<"success" | "danger">("success");

  const params = useParams();
  const demandaId = params.demandaId || "";

      useEffect(() => {
        const token = localStorage.getItem('token') || '';
        const email = localStorage.getItem('login') || '';
        
        const fetchIdUser = async () => {
            try {
                const response = await axios.get<Response>(`${API_URL}/estagiarios/buscarId/email/${email}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setEstagiarioNome(response.data.nome);
                setEstagiarioId(response.data.id);
            } catch (error) {
                  console.error(error);
              }
        };
        fetchIdUser();
      }, []);

  const cadastrarDemandaResposta = async (e: React.FormEvent) => {
    e.preventDefault();
    //const token = localStorage.getItem('token');
    try {
      await axios.post(`${API_URL}/demandas/responde/`, {
        demandaId,
        estagiarioId,
        resposta
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      setMesagemToast("Demanda Respondida com sucesso.");
      setToastVariant("success");
      setMostrarToast(true);
      limparCampos();
    } catch (error) {
      console.error(error);
      alert("Erro ao responder demanda movimento.");
    }
  };

  const limparCampos = () => {
    setResposta("");
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

        <h2 className="fw-bold text-dark mb-0">Responder Demanda</h2>
      </div>

     
      <Card className="shadow-sm p-4 border-0">
        <Form onSubmit={cadastrarDemandaResposta}>
          <Row className="mb-3">
            <Col md={6}>
              <Form.Group controlId="advogado">
                <Form.Label className="fw-semibold">Estagiario</Form.Label>
                <Form.Control
                  type="text"
                  value={estagiarioNome}
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
                <Form.Label className="fw-semibold">Resposta</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Resposta"
                  value={resposta}
                  onChange={(e) => setResposta(e.target.value)}
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
              <SaveIcon className="me-2" /> Cadastrar
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
