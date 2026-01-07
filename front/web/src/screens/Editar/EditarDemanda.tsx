import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import styles from "./EditarDemanda.module.css";
import { Toast, ToastContainer } from "react-bootstrap";
//import moment from 'moment'

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

export default function EditarDemanda() {
  const navigate = useNavigate();

  const [demanda, setDemanda] = useState<string>("");
  const [demandaStatus, setDemandaStatus] = useState<string>("");
  // const [prazoDocumentos, setPrazoDocumentos] = useState<string>("");
  // const [dataHoje, setDataHoje] = useState<Date>(new Date());
  // const [btnDisabled, setBtnDisabled] = useState<boolean>(false);
  // const [prazoDias, setPrazoDias] = useState<number>();
  const [prazoFinal, setPrazoFinal] = useState<string>("");

  // const [messageDataError, setMessageDataError] = useState<string>("");

  const [nomeEstagiario, setNomeEstagiario] = useState<string>("");
  // const [nomeEstagiarioSearch, setNomeEstagiarioSearch] = useState<string>("");
  // const [estagiarioId, setEstagiarioId] = useState<number>(0);
  // const [estagiarios, setEstagiarios] = useState<Estagiario[]>([]);

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const params = useParams();
  const demandaId = params.demandaId || '';

  // const page = 0;
  // const size = 20;

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
        setPrazoFinal(dados.prazoFinal);
        setDemandaStatus(dados.demandaStatus);

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
      await axios.patch(`${API_URL}/demandas/${demandaId}/change`, null,
        {
          headers: {
              Authorization: `Bearer ${token}`
          },
          params: {
            status: demandaStatus
          }
        }
      );

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

  // const formatarData = (dataValue: string) => {
  //   let numeros = dataValue.replace(/\D/g, "");

  //   setPrazoDias(0);

  //   if (numeros.length === 0) {
  //     setMessageDataError("");
  //     setPrazoFinal("");
  //   }

  //   if (numeros.length > 8) {
  //     numeros = numeros.substring(0, 8);
  //   }

  //   let formatado = numeros;

  //   if (numeros.length > 2) {
  //     formatado = numeros.substring(0, 2) + "/" + numeros.substring(2);
  //   }

  //   if (numeros.length > 4) {
  //     formatado =
  //       numeros.substring(0, 2) +
  //       "/" +
  //       numeros.substring(2, 4) +
  //       "/" +
  //       numeros.substring(4);
  //   }

  //   if (numeros.length === 8) {
  //     const dia = parseInt(numeros.substring(0, 2));
  //     const mes = parseInt(numeros.substring(2, 4));
  //     const ano = parseInt(numeros.substring(4, 8));

  //     const dataDigitada = new Date(ano, mes - 1, dia);
  //     const hoje = new Date();

  //     if (dataDigitada.getTime() < hoje.getTime()) {
  //       setPrazoFinal("")
  //       setMessageDataError("*Prazo inválido");
  //       setBtnDisabled(true);
  //       setPrazoDias(0);
  //     } else {
  //       setMessageDataError("");
  //       setBtnDisabled(false);
  //       setPrazoFinal(dataDigitada.toLocaleDateString('pt-BR'));
  //     }
  //   }


  //   setPrazoDocumentos(formatado);
  // };

  // const setPrazoFinalPorDias = (dias:number) => {
  //   if(dias === 0 || dias < 0) return;
  //   setPrazoDias(dias);
  //   const dataMoment = moment(toDateStringUs(prazoDocumentos));
  //   const dataAdicionada = dataMoment.add(dias, 'days');
  //   setPrazoFinal(dataAdicionada.format('DD/MM/YYYY'));

  // }

  // const toDateStringUs = (data:string) => {
  //   let diaMesAno = data.split("/");
  //   return diaMesAno[2] + "-" + diaMesAno[1] + "-" + diaMesAno[0];
  // }

  // const setEstagiario = (estagiario: Estagiario) => {
  //   setNomeEstagiario(estagiario.nome);
  //   setEstagiarioId(estagiario.id);
  //   setNomeEstagiarioSearch("");
  // };

  const selecionarDemandaStatus = async (e: any) => {
    setDemandaStatus(e.target.value);
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
          {/* {estagiarios.length > 0 && (
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
          )} */}
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Status da demanda</label>
          <select
            className={styles.input}
            onChange={selecionarDemandaStatus}
            value={demandaStatus}
            required
          >
            <option value="" disabled selected></option>
            <option value="Em Correção">Em Correção</option>
            <option value="Corrigido">Corrigido</option>
            <option value="Devolvido">Devolvido</option>
            {/* <option value="Dentro do Prazo">Dentro do Prazo</option>
            <option value="Fora do Prazo">Fora do Prazo</option> */}
          </select>
        </div>

        {/* <div className={styles.inputDivGroup}>
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
        </div> */}

        {/* <div className={styles.inputDivGroup}>
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
          
          <div className={styles.inputGroup}>
            <label className={styles.label}>Prazo Final</label>
            <input
              className={styles.input}
              placeholder="Prazo Final"
              value={prazoFinal}
              onChange={(e: any) => setPrazoDias(e.target.value)}
              disabled
            />
          </div>
        </div> */}

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
