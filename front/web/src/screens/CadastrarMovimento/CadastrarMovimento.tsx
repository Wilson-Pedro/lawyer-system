import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./CadastrarMovimento.module.css";

const API_URL = process.env.REACT_APP_API;

interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

interface Entity {
  id: number;
  nome: string;
}

interface Advogado extends Entity {
}


export default function CadastrarMovimento() {

  const navigate = useNavigate();

  const [numeroDoProcesso, setNumeroDoProcesso] = useState("");
  const [processoId, setProcessoId] = useState<number>(0);

  const [nomeAdvogado, setNomeAdvogado] = useState("");
  //const [nomeAdvogadoSearch, setNomeAdvogadoSearch] = useState("");
  const [advogadoId, setAdvogadoId] = useState<number>(0);
  //const [advogados, setAdvogados] = useState<Advogado[]>([]);

  const [movimento, setMovimento] = useState("");

  // const page = 0;
  // const size = 20;

  const params = useParams();
  const numeroDoProcessoParams = params.numeroDoProcesso || '';

  // BUSCAR ADVOGADO
  // useEffect(() => {
  //   const buscarAdvogado = async () => {
  //     if (nomeAdvogadoSearch.length < 2) {
  //       setAdvogados([]);
  //       return;
  //     }
  //     try {
  //       const response = await axios.get(`${API_URL}/advogados/buscar/${nomeAdvogadoSearch}?page=${page}&size=${size}`);
  //       const pageData: Page<Advogado> = response.data;
  //       setAdvogados(pageData.content);
  //     } catch (error) {
  //       console.log('Error ao tentar buscar advogados ', error);
  //     }

  //   };

  //   const delay = setTimeout(buscarAdvogado, 100);

  //   return () => clearTimeout(delay);
  // }, [nomeAdvogadoSearch]);

  // BUSCAR PROCESSO POR NÚMERO DO PROCESSO
  useEffect(() => {
    const buscarProcessoPorNumeroDoProcesso = async () => {
      try {
        const response = await axios.get(`${API_URL}/processos/numeroDoProcesso/${numeroDoProcessoParams}`);
        setNumeroDoProcesso(response.data.numeroDoProcesso);
        setProcessoId(response.data.id);
        setNomeAdvogado(response.data.advogadoNome);
        setAdvogadoId(response.data.advogadoId);
      } catch(error) {
        console.log("Error ao buscar processo pelo número do processo ", error);
      }
    }

    buscarProcessoPorNumeroDoProcesso();
  }, [numeroDoProcesso]);


  const cadastrarMovimento = async () => {
    try {
      await axios.post(`${API_URL}/movimentos/`, {
        processoId,
        advogadoId,
        movimento
      });
      alert("Movimento cadastrado com sucesso!");
      limparCampos();
    } catch (error) {
      console.error(error);
      alert("Erro ao cadastrar processo.");
    }
  };

  // const setAdvogado = (advogado: Advogado) => {
  //   setNomeAdvogado(advogado.nome);
  //   setAdvogadoId(advogado.id);
  //   setNomeAdvogadoSearch("");
  // }

  const limparCampos = () => {
    setMovimento("");
  };

  return (

    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate(`/processos/${numeroDoProcessoParams}/movimento`)}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastro de Movimento</h1>
      <form className={styles.form} onSubmit={cadastrarMovimento}>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Nº do Processo</label>
          <input
            className={styles.input}
            placeholder={numeroDoProcessoParams}
            value={numeroDoProcesso}
            required
            disabled
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Advogado</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do advogado"
            value={nomeAdvogado}
            required
            disabled
          />
          {/* {advogados.length > 0 && (
            <ul className={styles.ul}>
              {advogados.map((data) => (
                <li
                  className={styles.li}
                  key={data.id}
                  onClick={() => setAdvogado(data)}
                >{data.nome}</li>
              ))}
            </ul>
          )} */}
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Movimento</label>
          <input
            className={styles.input}
            placeholder="Movimento"
            value={movimento}
            onChange={(e) => setMovimento(e.target.value)}
            required
          />
        </div>

        <button type="submit" className={styles.button}>
          Cadastrar Movimento
        </button>
      </form>
    </div>
  );
}
