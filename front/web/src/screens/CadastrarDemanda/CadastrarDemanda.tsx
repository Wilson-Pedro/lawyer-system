import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import styles from "./CadastrarDemanda.module.css";
import { Toast, ToastContainer } from "react-bootstrap";
import moment from 'moment'
import Input from "../../components/Input/Input";

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

export interface Assistido extends Entity {}

export interface Advogado extends Entity {}

export interface Estagiario extends Entity {}

export default function CadastrarDemanda() {
  const navigate = useNavigate();

  const [demanda, setDemanda] = useState<string>("");
  const [prazoDocumentos, setPrazoDocumentos] = useState<string>("");
  const [dataHoje, setDataHoje] = useState<Date>(new Date());
  const [btnDisabled, setBtnDisabled] = useState<boolean>(false);
  const [prazoDias, setPrazoDias] = useState<number>();
  const [prazoFinal, setPrazoFinal] = useState<string>("");

  const [messageDataError, setMessageDataError] = useState<string>("");

  const [nomeEstagiario, setNomeEstagiario] = useState<string>("");
  const [nomeEstagiarioSearch, setNomeEstagiarioSearch] = useState<string>("");
  const [estagiarioId, setEstagiarioId] = useState<number>(0);
  const [estagiarios, setEstagiarios] = useState<Estagiario[]>([]);

  
  const [nomeAdvogado, setNomeAdvogado] = useState("");
  const [nomeAdvogadoSearch, setNomeAdvogadoSearch] = useState("");
  const [advogadoId, setAdvogadoId] = useState<number>(0);
  const [advogados, setAdvogados] = useState<Advogado[]>([]);

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">(
    "success"
  );

  const page = 0;
  const size = 20;

  useEffect(() => {
    const buscarEstagiario = async () => {
      if (nomeEstagiarioSearch.length < 2) {
        setEstagiarios([]);
        return;
      }
      try {
        const response = await axios.get(
          `${API_URL}/estagiarios/buscar/${nomeEstagiarioSearch}?page=${page}&size=${size}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const pageData: Page<Estagiario> = response.data;
        setEstagiarios(pageData.content);
      } catch (error) {
        console.log("Error ao tentar buscar estagiarios ", error);
      }
    };

    const delay = setTimeout(buscarEstagiario, 100);

    return () => clearTimeout(delay);
  }, [nomeEstagiarioSearch]);

  
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

  const cadastrarDemanda = async (e: any) => {
    e.preventDefault();

    try {
      await axios.post(
        `${API_URL}/demandas/`,
        {
          demanda,
          estagiarioId,
          advogadoId,
          demandaStatusAluno: "Em Correção",
          demandaStatusProfessor: "Aguardando Professor",
          prazoDocumentos,
          diasPrazo: prazoDias,
          tempestividade: "Dentro do Prazo"
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setMostrarToast(true);
      setMensagemToast("Demanda cadastrada com sucesso.");
      setVarianteToast("success");
      limparCampos();
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao Cadastrar Demanda");
      setVarianteToast("danger");
    }
  };

  const formatarData = (dataValue: string) => {
    let numeros = dataValue.replace(/\D/g, "");

    setPrazoDias(0);

    if (numeros.length === 0) {
      setMessageDataError("");
      setPrazoFinal("");
    }

    if (numeros.length > 8) {
      numeros = numeros.substring(0, 8);
    }

    let formatado = numeros;

    if (numeros.length > 2) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2);
    }

    if (numeros.length > 4) {
      formatado =
        numeros.substring(0, 2) +
        "/" +
        numeros.substring(2, 4) +
        "/" +
        numeros.substring(4);
    }

    if (numeros.length === 8) {
      const dia = parseInt(numeros.substring(0, 2));
      const mes = parseInt(numeros.substring(2, 4));
      const ano = parseInt(numeros.substring(4, 8));

      const dataDigitada = new Date(ano, mes - 1, dia);
      const hoje = new Date();

      if (dataDigitada.getTime() < hoje.getTime()) {
        setPrazoFinal("")
        setMessageDataError("*Prazo inválido");
        setBtnDisabled(true);
        setPrazoDias(0);
      } else {
        setMessageDataError("");
        setBtnDisabled(false);
        setPrazoFinal(dataDigitada.toLocaleDateString('pt-BR'));
      }
    }


    setPrazoDocumentos(formatado);
  };

  const setPrazoFinalPorDias = (dias:number) => {
    if(dias === 0 || dias < 0) return;
    setPrazoDias(dias);
    const dataMoment = moment(toDateStringUs(prazoDocumentos));
    const dataAdicionada = dataMoment.add(dias, 'days');
    setPrazoFinal(dataAdicionada.format('DD/MM/YYYY'));

  }

  const toDateStringUs = (data:string) => {
    let diaMesAno = data.split("/");
    return diaMesAno[2] + "-" + diaMesAno[1] + "-" + diaMesAno[0];
  }

  const setEstagiario = (estagiario: Estagiario) => {
    setNomeEstagiario(estagiario.nome);
    setEstagiarioId(estagiario.id);
    setNomeEstagiarioSearch("");
  }; 

  const setAdvogado = (advogado: Advogado) => {
    setNomeAdvogado(advogado.nome);
    setAdvogadoId(advogado.id);
    setNomeAdvogadoSearch("");
  }

  const limparCampos = () => {
    setNomeEstagiarioSearch("");
    setNomeEstagiario("")
    setDemanda("Em Correção")
    setNomeAdvogadoSearch("")
    setNomeAdvogado("");
    setPrazoDocumentos("");
    setPrazoDias(0);
  };

  const token = localStorage.getItem("token");
  if (!token) return <Navigate to="/login" />;

  return (
    <div className={styles.container}>
      <button
        className={styles.backButton}
        onClick={() => navigate("/cadastrar")}
      >
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastro de Demanda</h1>
      <form className={styles.form} onSubmit={cadastrarDemanda}>
        
        <Input
          value={demanda}
          title="Demanda"
          setValue={setDemanda}
          required={true}
        />

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
                >
                  {data.nome}
                </li>
              ))}
            </ul>
          )}
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

        <div className={styles.inputDivGroup}>

          <div className={styles.inputGroup}>
            <label className={styles.label}>Data de Atendimento</label>
            <input
              className={styles.input}
              value={dataHoje.toLocaleDateString("pt-BR")}
              disabled
            />
          </div>

          <div className={styles.inputGroup}>
            <label className={styles.label}>
              Prazo dos Documentos
              <span className={styles.messageError}>{messageDataError}</span>
            </label>
            <input
              className={styles.input}
              placeholder="Prazo dos Documentos (DD/MM/AAAA)"
              value={prazoDocumentos}
              onChange={(e) => formatarData(e.target.value)}
              required
            />
          </div>
        </div>

        <div className={styles.inputDivGroup}>

          <div className={styles.inputGroup}>
            <label className={styles.label}>Dias para o Prazo Final</label>
            <input
              type="number"
              className={styles.input}
              placeholder="Digite a quantidade de dias para o prazo"
              value={prazoDias}
              onChange={(e: any) => setPrazoFinalPorDias(e.target.value)}
              disabled={btnDisabled}
              required
            />
          </div>

          <Input
            value={prazoFinal}
            title="Prazo Final"
            setValue={setPrazoDias}
            required={true}
            disabled={true}
          />
        
        </div>

        <button type="submit" className={styles.button}>
          Cadastrar Demanda
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
