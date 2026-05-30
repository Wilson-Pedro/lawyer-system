import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import styles from "./EditarDemanda.module.css";
import { Toast, ToastContainer } from "react-bootstrap";
import { scrollToTop } from "../../utils/Utils";

const API_URL = process.env.REACT_APP_API;

export default function EditarDemanda() {
  const navigate = useNavigate();

  const [demanda, setDemanda] = useState<string>("");

  const [demandaStatusAluno, setDemandaStatusAluno] = useState<string>("");
  const [demandaStatusProfessor, setDemandaStatusProfessor] = useState<string>("");
  const [demandaStatusAdvogado, setDemandaStatusAdvogado] = useState<string>("");
  
  const [prazoFinal, setPrazoFinal] = useState<string>("");

  const [nomeEstagiario, setNomeEstagiario] = useState<string>("");
  const [nomeProfessor, setNomeProfessor] = useState<string>("");
  const [nomeAdvogado, setNomeAdvogado] = useState<string>("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const params = useParams();
  const demandaId = params.demandaId || '';

  useEffect(() => {

    const token = localStorage.getItem('token');

    const fetchDemanda = async () => {
      try {

        const response = await axios.get(`${API_URL}/demandas/${demandaId}`,{
          headers: {
              Authorization: `Bearer ${token}`
          }
        });
        const dados = response.data;
        setDemanda(dados.demanda);
        setNomeEstagiario(dados.estagiarioNome);
        setNomeProfessor(dados.professorNome);
        setNomeAdvogado(dados.advogadoNome);
        setPrazoFinal(dados.prazoFinal);
        setDemandaStatusAluno(dados.demandaStatusAluno);
        setDemandaStatusProfessor(dados.demandaStatusProfessor);
        setDemandaStatusAdvogado(dados.demandaStatusAdvogado);

      } catch(error) {
        console.log(error);
      }
    }

    fetchDemanda();
  }, []);

  const editarDemanda = async (e:any) => {
    e.preventDefault();

     const token = localStorage.getItem('token');
     
    try {
      await axios.put(`${API_URL}/demandas/${demandaId}/update`, {
        demandaStatusAluno: demandaStatusAluno,
        demandaStatusProfessor: demandaStatusProfessor,
        demandaStatusAdvogado: demandaStatusAdvogado,
      },
        {
          headers: {
              Authorization: `Bearer ${token}`
          }
        }
      );

      scrollToTop();

      setMostrarToast(true);
      setMensagemToast("Demanda editada com sucesso.");
      setVarianteToast("success");
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao editar Demanda");
      setVarianteToast("danger");
    }
  };

  const selecionarDemandaStatusAluno = async (e: any) => {
    setDemandaStatusAluno(e.target.value);
  };

  const selecionarDemandaStatusProfessor = async (e: any) => {
    setDemandaStatusProfessor(e.target.value);
  };

  const selecionarDemandaStatusAdvogado = async (e: any) => {
    setDemandaStatusAdvogado(e.target.value);
  };

  const token = localStorage.getItem("token");
  if (!token) return <Navigate to="/login" />;

  return (
    <div className={styles.container}>
      <button
        className={styles.backButton}
        onClick={() => navigate("/demandas")}
      >
        ← Voltar
      </button>

      <h1 className={styles.title}>Editar Demanda</h1>
      <form className={styles.form} onSubmit={editarDemanda}>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Demanda</label>
          <input
            className={styles.input}
            placeholder="Demanda"
            value={demanda}
            onChange={(e) => setDemanda(e.target.value)}
            required
            disabled
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Estagiário</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do estagiário"
            value={nomeEstagiario}
            onChange={(e) => setNomeEstagiario(e.target.value)}
            required
            disabled
          />

        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Professor</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do estagiário"
            value={nomeProfessor}
            onChange={(e) => setNomeEstagiario(e.target.value)}
            required
            disabled
          />

        </div>


        <div className={styles.inputGroup}>
          <label className={styles.label}>Advogado</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do estagiário"
            value={nomeAdvogado}
            onChange={(e) => setNomeEstagiario(e.target.value)}
            required
            disabled
          />

        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Status da demanda (Aluno)</label>
          <select
            className={styles.input}
            onChange={selecionarDemandaStatusAluno}
            value={demandaStatusAluno}
            required
          >
            <option value="" disabled selected></option>
            <option value="Em Correção">Em Correção</option>
            <option value="Corrigido">Corrigido</option>
            <option value="Devolvido">Devolvido</option>
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Status da demanda (Proessor)</label>
          <select
            className={styles.input}
            onChange={selecionarDemandaStatusProfessor}
            value={demandaStatusProfessor}
            required
          >
            <option value="" disabled selected></option>
            <option value="Em Correção">Em Correção</option>
            <option value="Corrigido">Corrigido</option>
            <option value="Devolvido">Devolvido</option>
          </select>
        </div>

        
        <div className={styles.inputGroup}>
          <label className={styles.label}>Status da demanda (Advogado)</label>
          <select
            className={styles.input}
            onChange={selecionarDemandaStatusAdvogado}
            value={demandaStatusAdvogado}
            required
          >
            <option value="" disabled selected></option>
            <option value="Em Correção">Em Correção</option>
            <option value="Corrigido">Corrigido</option>
            <option value="Devolvido">Devolvido</option>
          </select>
        </div>

          <div className={styles.inputGroup}>
            <label className={styles.label}>Prazo Final</label>
            <input
              className={styles.input}
              placeholder="Prazo Final"
              value={prazoFinal}
              onChange={(e: any) => setPrazoFinal(e.target.value)}
              disabled
            />
          </div>

        <button type="submit" className={styles.button}>
          Editar Demanda
        </button>

        <ToastContainer
          position="top-end"
          className="p-3"
          style={{ zIndex: 9999 }}
        >
          <Toast
            show={mostrarToast}
            onClose={() => setMostrarToast(false)}
            bg={varianteToast}
            delay={3000}
            autohide
          >
            <Toast.Body className="text-white fw-semibold">
              {mensagemToast}
            </Toast.Body>
          </Toast>
        </ToastContainer>
      </form>
    </div>
  );
}
