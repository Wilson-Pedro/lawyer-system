import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams, Navigate } from "react-router-dom";
import styles from "./EditarProcesso.module.css";
import { Toast, ToastContainer } from "react-bootstrap";
import Select from "react-select";
import { scrollToTop } from './../../utils/Utils';

const API_URL = process.env.REACT_APP_API;

export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export interface Entity {
  id: number;
  nome: string;
}

export interface Assistido extends Entity {
}

export interface Advogado extends Entity {
}

export interface Estagiario extends Entity {
}

export default function EditarProcesso() {
  const navigate = useNavigate();

  const [numeroDoProcesso, setNumeroDoProcesso] = useState<string | "">("");
  const [numeroDoProcessoPje, setNumeroDoProcessoPje] = useState<string | "">("");
  const [assunto, setAssunto] = useState("");
  const [vara, setVara] = useState("");
  const [responsavel, setResponsavel] = useState("");

  const [areaDoDireito, setAreaDeDireito] = useState("");
  const [areasDoDireito, setAreasDeDireito] = useState<string[]>([]);

  const [tribunal, setTribunal] = useState("");
  const [tribunais, setTribunais] = useState<string[]>([]);

  const [prazo, setPrazo] = useState<string | "">("");

  const [messageDataError, setMessageDataError] = useState("");

  const [nomeAssistido, setNomeAssistido] = useState("");
  const [assistidoId, setAssistidoId] = useState<number>(0);

  const [nomeAdvogado, setNomeAdvogado] = useState("");
  const [nomeAdvogadoSearch, setNomeAdvogadoSearch] = useState("");
  const [advogadoId, setAdvogadoId] = useState<number>(0);
  const [advogados, setAdvogados] = useState<Advogado[]>([]);

  const [nomeEstagiario, setNomeEstagiario] = useState("");
  const [nomeEstagiarioSearch, setNomeEstagiarioSearch] = useState("");
  const [estagiarioId, setEstagiarioId] = useState<number>(0);
  const [estagiarios, setEstagiarios] = useState<Estagiario[]>([]);

  const [statusProcesso, setStatusProcesso] = useState<string[]>([]);

  const [statusDoProcesso, setStatusDoProcesso] = useState("");
  const [partesEnvolvidas, setPartesEnvolvidas] = useState("");
  const [ultimaAtualizacao, setUltimaAtualizacao] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  //const [processoDto, setProcessoDto] = useState<ProcessoDto | null>(null);

  const params = useParams();
  const processoId = params.processoId || '';

  const page = 0;
  const size = 20;

  useEffect(() => {

    const buscarAreasDoDireito = async () => {

      try {

        const response = await axios.get(`${API_URL}/processos/areasDoDireito`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setAreasDeDireito(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    const buscarTribunais = async () => {

      try {

        const response = await axios.get(`${API_URL}/processos/tribunais`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setTribunais(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    const buscarStatusDoProcesso = async () => {

      try {

        const response = await axios.get(`${API_URL}/processos/processoStatus`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setStatusProcesso(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    buscarAreasDoDireito();
    buscarTribunais();
    buscarStatusDoProcesso();
  }, []);

  useEffect(() => {

    const buscarProcessoPorId = async () => {
      try {
        const response = await axios.get(`${API_URL}/processos/${processoId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        const processo = response.data;
        setAssistidoId(processo.assistidoId);
        setNomeAssistido(processo.assistidoNome);
        setAssunto(processo.assunto);
        setVara(processo.vara);
        setNumeroDoProcesso(processo.numeroDoProcesso);
        setNumeroDoProcessoPje(processo.numeroDoProcessoPje);
        setResponsavel(processo.responsavel);

        setAdvogadoId(processo.advogadoId);
        setNomeAdvogadoSearch(processo.advogadoNome);

        setEstagiarioId(processo.estagiarioId);
        setNomeEstagiarioSearch(processo.estagiarioNome);

        setStatusDoProcesso(processo.statusDoProcesso);
        setAreaDeDireito(processo.areaDoDireito);
        setTribunal(processo.tribunal);
        setPrazo(processo.prazoFinal);
        setPartesEnvolvidas(processo.partesEnvolvidas);
        setUltimaAtualizacao(processo.ultimaAtualizacao);

      } catch(error) {
          console.log(error);
          setMostrarToast(true);
          setMensagemToast("Error ao buscar processo");
          setVarianteToast("danger")
      }
  }

  buscarProcessoPorId();

  }, [processoId])

  useEffect(() => {
    const buscarAdvogado = async () => {
      if (nomeAdvogadoSearch.length < 2) {
        setAdvogados([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/advogados/buscar/${nomeAdvogadoSearch}?page=${page}&size=${size}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        const pageData: Page<Advogado> = response.data;
        setAdvogados(pageData.content);
      } catch (error) {
        setMensagemToast("Error ao tentar buscar advogados");
      }

    };

    const delay = setTimeout(buscarAdvogado, 100);

    return () => clearTimeout(delay);
  }, [nomeAdvogadoSearch]);

  useEffect(() => {
    const buscarEstagiario = async () => {
      if(nomeEstagiarioSearch.length < 2) {
        setEstagiarios([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/estagiarios/buscar/${nomeEstagiarioSearch}?page=${page}&size=${size}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
       });
        const pageData: Page<Estagiario> = response.data;
        setEstagiarios(pageData.content);

      } catch(error) {
        console.log("Error ao tentar buscar estagiarios ", error)
      }
    };

    const delay = setTimeout(buscarEstagiario, 100);

    return () => clearTimeout(delay);
  }, [nomeEstagiarioSearch]);


  const atualizarProcesso = async () => {

    try {
      await axios.put(`${API_URL}/processos/${processoId}`, {
        assistidoId,
        numeroDoProcesso,
        numeroDoProcessoPje,
        assunto,
        vara,
        prazoFinal: prazo,
        responsavel,
        advogadoId,
        estagiarioId,
        areaDoDireito,
        tribunal,
        statusDoProcesso,
        partesEnvolvidas,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      scrollToTop();
      
      setMostrarToast(true);
      setMensagemToast("Processo atualizado com sucesso.");
      setVarianteToast("success");

    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao atualizar Processo.");
      setVarianteToast("danger");
    }
  };

  const formatarData = (dataValue: string) => {
    let numeros = dataValue.replace(/\D/g, "");

    if (numeros.length > 8) {
      numeros = numeros.substring(0, 8);
    }

    let formatado = numeros;

    if (numeros.length > 2) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2);
    }

    if (numeros.length > 4) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2, 4) + "/" + numeros.substring(4);
    }

    if (numeros.length === 8) {
      const dia = parseInt(numeros.substring(0, 2));
      const mes = parseInt(numeros.substring(2, 4));
      const ano = parseInt(numeros.substring(4, 8));

      const dataDigitada = new Date(ano, mes - 1, dia);
      const hoje = new Date();

      if (dataDigitada.getTime() < hoje.getTime()) {
        setMessageDataError("*Prazo inválida");
      } else {
        setMessageDataError("");
      }
    }

    setPrazo(formatado);
  }

  const selecionarAreaDoDireito = async (e:any) => {
    setAreaDeDireito(e.target.value);
  }

  const selecionarTribunal = async (e:any) => {
    setTribunal(e.target.value);
  }

  const setAdvogado = (advogado: Advogado) => {
    setNomeAdvogado(advogado.nome);
    setAdvogadoId(advogado.id);
    setNomeAdvogadoSearch("");
  }

  const setEstagiario = (estagiario: Estagiario) => {
    setNomeEstagiario(estagiario.nome);
    setEstagiarioId(estagiario.id);
    setNomeEstagiarioSearch("");
  }

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (

    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate("/processos")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Editar Processo</h1>
      <form className={styles.form} onSubmit={(e) => { e.preventDefault(); atualizarProcesso(); }}>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Assistido</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do assistido"
            value={nomeAssistido}
            required
            disabled
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Nº do Processo</label>
          <input
            className={styles.input}
            placeholder="Nº do Processo"
            value={numeroDoProcesso}
            required
            disabled
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Assunto</label>
          <input
            className={styles.input}
            placeholder="Assunto"
            value={assunto}
            onChange={(e) => setAssunto(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Vara</label>
          <input
            className={styles.input}
            placeholder="Vara"
            value={vara}
            onChange={(e) => setVara(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Nº do processo do PJE</label>
          <input
            className={styles.input}
            placeholder="Nº do processo do PJE"
            value={numeroDoProcessoPje}
            onChange={(e) => setNumeroDoProcessoPje(e.target.value)}
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Responsável</label>
          <input
            className={styles.input}
            placeholder="Responsável"
            value={responsavel}
            onChange={(e) => setResponsavel(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Advogado</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do advogado"
            value={nomeAdvogadoSearch || nomeAdvogado}
            onChange={(e) => setNomeAdvogadoSearch(e.target.value)}
            required
          />
          {advogados.length > 0 && (
            <ul className={styles.ul}>
              {advogados.map((data) => (
                <li
                  className={styles.li}
                  key={data.id}
                  onClick={() => setAdvogado(data)}
                >{data.nome}</li>
              ))}
            </ul>
          )}
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Estagiário</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do estagiário"
            value={nomeEstagiarioSearch || nomeEstagiario}
            onChange={(e) => setNomeEstagiarioSearch(e.target.value)}
            required
          />
          {estagiarios.length > 0 && (
            <ul className={styles.ul}>
              {estagiarios.map((data) => (
                <li
                  className={styles.li}
                  key={data.id}
                  onClick={() => setEstagiario(data)}
                >{data.nome}</li>
              ))}
            </ul>
          )}
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Área de Direito</label>
          <select 
            className={styles.input} 
            value={areaDoDireito} 
            onChange={selecionarAreaDoDireito} 
            required
          >
            <option value="" disabled selected></option>
            {areasDoDireito.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Tribunal</label>
          <select 
            className={styles.input}
            value={tribunal}
            onChange={selecionarTribunal}
            required
          >
            <option value="" disabled selected></option>
            {tribunais.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Status Do Processo</label>
          <select 
            className={styles.input}
            value={statusDoProcesso}
            onChange={selecionarTribunal}
            required
          >
            <option value="" disabled selected></option>
            {statusProcesso.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Prazo <span className={styles.messageError}>{messageDataError}</span></label>
          <input
            className={styles.input}
            placeholder="Prazo (DD/MM/AAAA)"
            value={prazo}
            onChange={(e) => formatarData(e.target.value)}
            required
          />
        </div>
     
        <div className={styles.inputGroup}>
          <label className={styles.label}>Partes Envolvidas</label>
          <input
            className={styles.input}
            placeholder="Partes Envolvidas"
            value={partesEnvolvidas}
            onChange={(e) => setPartesEnvolvidas(e.target.value)}
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Última atualização</label>
          <input
            className={styles.input}
            placeholder="Última atualização"
            value={ultimaAtualizacao}
            disabled
          />
        </div>

        <button type="submit" className={styles.button}>
          Atualizar Processo
        </button>

        <ToastContainer position="top-end" className="p-3" style={{ zIndex: 9999 }}>
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
