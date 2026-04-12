import React, { useState, useEffect, ChangeEvent } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
// import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { EditIcon } from "../../Icons/Icon";
import Paginacao from "../../components/Paginacao/Paginacao";
import styles from "./Usuarios.module.css";
import { startOfDay } from "date-fns";
import { Toast, ToastContainer } from "react-bootstrap";

const API_URL = process.env.REACT_APP_API;

interface ResponseMinDto {
  id: string;
  nome: string;
  email: string;
  telefone: string;
  matricula: string;
  periodo: string;
  usuarioStatus: string;
  registro: string;
}

interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export default function Usuarios() {
  const [usuarios, setUsuarios] = useState<ResponseMinDto[]>([]);
  const [usuariosFiltrados, setUsuariosFiltrados] = useState<ResponseMinDto[]>(
    [],
  );
  const [busca, setBusca] = useState("");
  const [usuariosFiltro, setUsuariosFiltro] = useState<string>("Estagiário");
  const [uriEdit, setUriEdit] = useState("/usuarios/estagiario/editar/");
  const [tableLabels, setTableLabes] = useState<string[]>([]);

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  const [primeiraPagina, setPrimeiraPagina] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [totalElements, setTotalElements] = useState(0);
  const [paginas, setPaginas] = useState<number[]>([]);
  const [ultimaPagina, setUltimaPagina] = useState<number>(10);
  const [paginaAtual, setPaginaAtual] = useState<number>(0);
  const [dataDeDesativacao, setDataDeDesativacao] = useState<string>('');

  const [usuarioStatus, setUuarioStatus] = useState<string>("");
  const [tipoUsuario, setTipoUsuario] = useState<string>('Estagiário');

  const [mostrarUltimaPagina, setMostrarUltimaPagina] =
    useState<boolean>(false);
  const [mostrarPrimeiraPagina, setMostrarPrimeiraPagina] =
    useState<boolean>(false);

  const [mostrarFiltroDesativao, setMostrarFiltroDesativacao] =
    useState<boolean>(true);

  const [mostrarBtnDataDesativacao, setMostrarBtnDataDesativacao] =
    useState<boolean>(false);

  const [btnMsgDesativar, setBtnMsgDesativar] = useState("Desativar Usuários");

  const [messageDataError, setMessageDataError] = useState<string>("");
  const [idList, setIdList] = useState<number[]>([]);
  
  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const navigate = useNavigate();

  const rotasParaDesativar: Record<string, string> = {
    "Coordenador do curso": "/atores/desativar/usuarios",
    Secretário: "/atores/desativar/usuarios",
    Professor: "/atores/desativar/usuarios",
    Estagiário: "/estagiarios/desativar/usuarios",
    Advogado: "/advogados/desativar/usuarios",
  };

  const rotaParaDesativar = rotasParaDesativar[usuariosFiltro];

  const desativarUsuario = async () => {
    try {
      await axios.patch(
        `${API_URL}${rotaParaDesativar}`,
        {
          ids: idList,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      setIdList([]);
      setMostrarFiltroDesativacao(true);
      setBtnMsgDesativar("Desativar Usuários");
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (!usuariosFiltrados) return;
    const token = localStorage.getItem("token");

    const rotas: Record<string, string> = {
      "Coordenador do curso": "/atores/tipo/Coordenador do curso",
      Secretário: "/atores/tipo/Secretário",
      Professor: "/atores/tipo/Professor",
      Estagiário: "/estagiarios",
      Advogado: "/advogados",
      Assistido: "/assistidos",
    };

    const uris: Record<string, string> = {
      "Coordenador do curso": "/usuarios/editar/",
      Secretário: "/usuarios/editar/",
      Professor: "/usuarios/editar/",
      Estagiário: "/usuarios/estagiario/editar/",
      Advogado: "/usuarios/advogado/editar/",
      Assistido: "/usuarios/assistido/editar/",
    };

    const tableHeaders: Record<string, string[]> = {
      Estagiário: [
        "Nome",
        "Matrícula",
        "E-mail",
        "Telefone",
        "Estágio",
        "Status",
      ],
      Assistido: ["Nome", "E-mail", "Registro"],
      default: ["Nome", "E-mail", "Status", "Registro"],
    };

    const rota = rotas[usuariosFiltro];
    if (!rota) {
      setUsuarios([]);
      setUsuariosFiltrados([]);
      return;
    }

    const uri = uris[usuariosFiltro];
    setUriEdit(uri);

    if (usuariosFiltro === "Estagiário") {
      setTableLabes(tableHeaders["Estagiário"]);
    } else if (usuariosFiltro === "Assistido") {
      setTableLabes(tableHeaders["Assistido"]);
    } else {
      setTableLabes(tableHeaders["default"]);
    }

    const fecthUsuarios = async () => {
      try {
        const response = await axios.get(
          `${API_URL}${rota}?page=${page}&size=${size}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        );

        const pages: Page<ResponseMinDto> = response.data;
        const dados = pages.content;
        setUsuarios(dados);
        setUsuariosFiltrados(dados);
        setTotalPages(pages.totalPages);
        setTotalElements(pages.totalElements);
      } catch (error) {
        console.log(error);
      }
    };
    fecthUsuarios();
  }, [usuariosFiltro, page, size]);

  const cadastrarDataAtivacaoDesativacao = async (e:any) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    try {
      await axios.put(`${API_URL}/auth/definir/data/ativarDesativar`, {
        tipoUsuario: tipoUsuario,
        dataDeDesativacao: dataDeDesativacao,
        usuarioStatus: usuarioStatus
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setMensagemToast("Data definida com sucesso!");
      setVarianteToast("success");
      setMostrarToast(true);

    } catch (error) {

      setMensagemToast("Erro ao definir Data.");
      setVarianteToast("danger");
      setMostrarToast(true);
    }
  };

  useEffect(() => {
    let dados = [...usuarios];

    if (busca.trim() !== "" && usuariosFiltro !== "Estagiário") {
      dados = dados.filter(
        (usuario) =>
          usuario.nome.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.email.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.matricula.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.telefone.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.periodo.toLowerCase().includes(busca.toLowerCase()) ||
          usuario.registro.toLowerCase().includes(busca.toLowerCase()),
      );
    }

    setUsuariosFiltrados(dados);
  }, [busca, usuarios]);

  const getUsuarioStatusClass = (status: string) => {
    switch (status) {
      case "Ativo":
        return "text-info fw-bold";
      case "Inativo":
        return "text-danger fw-bold";
      default:
        return "";
    }
  };

  const selecionarTipoDeUsuario = (usuario: string) => {
    setPage(0);
    setPaginaAtual(0);
    setPrimeiraPagina(0);
    setUsuariosFiltro(usuario);
    zeraIdLista();
  };

  const desativarUsuariosFiltro = () => {
    setMostrarFiltroDesativacao(!mostrarFiltroDesativao);
    setBtnMsgDesativar(
      mostrarFiltroDesativao === false ? "Desativar Usuários" : "Tirar Filtro",
    );
    setMostrarBtnDataDesativacao(!mostrarBtnDataDesativacao);
    setIdList([]);
  };

  const zeraIdLista = () => {
    setIdList([]);
    setMostrarFiltroDesativacao(true);
    setBtnMsgDesativar("Desativar Usuários");
  };

  const adicionarIdsParaLista = (id: number) => {
    if (id !== null && !idList.includes(id)) {
      setIdList((ids) => [...ids, id]);
    } else if (idList.includes(id)) {
      let listaFiltrada = idList.filter((item) => item !== id);
      setIdList(listaFiltrada);
    }
  };

  const formatarData = (dataValue: string) => {
    let numeros = dataValue.replace(/\D/g, "");

    if (numeros.length === 0) {
      setMessageDataError("");
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

      const dataDigitada = startOfDay(new Date(ano, mes - 1, dia));
      const hoje = startOfDay(new Date());
      

      if (dataDigitada.getTime() < hoje.getTime()) {
        setMessageDataError("*Data inválida");
      } else {
        setMessageDataError("");
      }
    }


    setDataDeDesativacao(formatado);
  };

  const selecionarUsuarioStatus = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const status = e.target.value;
    setUuarioStatus(status);
  };

  const selecionarTipoUsuario = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setTipoUsuario(e.target.value);
  };

  const token = localStorage.getItem("token");
  if (!token) return <Navigate to="/login" />;

  return (
    <div className="min-vh-100 d-flex flex-column bg-light">
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm px-4">
        <span className="navbar-brand fw-bold fs-4">Gerenciar Usuário</span>
        <span></span>
        <button
          className="btn btn-outline-light ms-auto"
          onClick={() => navigate("/home/admin")}
        >
          ← Voltar
        </button>
      </nav>

      <div className="container my-5 flex-grow-1">
        <div className="d-flex flex-wrap justify-content-between align-items-center mb-4">
          <input
            type="text"
            className="form-control w-50 mb-2 mb-sm-0"
            placeholder="Buscar por nome ou email"
            value={busca}
            onChange={(e: ChangeEvent<HTMLInputElement>) =>
              setBusca(e.target.value)
            }
          />

          <select
            className="form-select w-auto"
            value={usuariosFiltro}
            onChange={(e) => selecionarTipoDeUsuario(e.target.value)}
          >
            <option value="Estagiário" onClick={zeraIdLista}>
              Estagiário
            </option>
            <option value="Coordenador do curso" onClick={zeraIdLista}>
              Coordenador do curso
            </option>
            <option value="Secretário" onClick={zeraIdLista}>
              Secretário
            </option>
            <option value="Professor" onClick={zeraIdLista}>
              Professor
            </option>
            <option value="Advogado" onClick={zeraIdLista}>
              Advogado
            </option>
            <option value="Assistido" onClick={zeraIdLista}>
              Assistido
            </option>
          </select>
        </div>

        {usuariosFiltro !== "Assistido" ? (
          <div className={styles.divBtn}>
            {btnMsgDesativar !== "Desativar Usuários" && (
              <form onSubmit={desativarUsuario}>
                <button
                  type="submit"
                  className="btn btn-warning m-1"
                  disabled={idList.length === 0}
                >
                  Desativar
                </button>
              </form>
            )}

            <button
              onClick={desativarUsuariosFiltro}
              className="btn btn-primary m-1"
            >
              {btnMsgDesativar}
            </button>
            <button
              disabled={mostrarBtnDataDesativacao}
              className="btn btn-primary m-1"
              data-bs-toggle="modal"
              data-bs-target="#staticBackdrop"
            >
              Definir Data de Desativação
            </button>
          </div>
        ) : (
          <></>
        )}
        {usuariosFiltrados.length > 0 ? (
          <div className="table-responsive shadow-sm rounded">
            <table className="table table-hover align-middle bg-white rounded overflow-hidden">
              <thead className="table-dark">
                <tr>
                  {!mostrarFiltroDesativao && usuariosFiltro !== "Assistido" ? (
                    <th>Selecionar</th>
                  ) : (
                    <></>
                  )}
                  {tableLabels.map((label) => (
                    <th>{label}</th>
                  ))}
                  <th className="text-center">Editar</th>
                </tr>
              </thead>
              <tbody>
                {usuariosFiltrados.map((usuario) => (
                  <tr key={usuario.id}>
                    {usuariosFiltro === "Estagiário" ? (
                      <>
                        {!mostrarFiltroDesativao ? (
                          <td className="text-center">
                            <input
                              className="form-check-input"
                              onClick={() =>
                                adicionarIdsParaLista(Number(usuario.id))
                              }
                              type="checkbox"
                            />
                          </td>
                        ) : (
                          <></>
                        )}
                        <td>{usuario.nome}</td>
                        <td>{usuario.matricula}</td>
                        <td>{usuario.email}</td>
                        <td>{usuario.telefone}</td>
                        <td>{usuario.periodo}</td>
                        <td
                          className={getUsuarioStatusClass(
                            usuario.usuarioStatus,
                          )}
                        >
                          {usuario.usuarioStatus}
                        </td>
                      </>
                    ) : usuariosFiltro === "Assistido" ? (
                      <>
                        <td>{usuario.nome}</td>
                        <td>{usuario.email}</td>
                        <td>{usuario.registro}</td>
                      </>
                    ) : (
                      <>
                        {!mostrarFiltroDesativao ? (
                          <td className="text-center">
                            <input
                              className="form-check-input"
                              onClick={() =>
                                adicionarIdsParaLista(Number(usuario.id))
                              }
                              type="checkbox"
                            />
                          </td>
                        ) : (
                          <></>
                        )}
                        <td>{usuario.nome}</td>
                        <td>{usuario.email}</td>
                        <td
                          className={getUsuarioStatusClass(
                            usuario.usuarioStatus,
                          )}
                        >
                          {usuario.usuarioStatus}
                        </td>
                        <td>{usuario.registro}</td>
                      </>
                    )}
                    <td className="text-center">
                      <button
                        className="btn btn-sm btn-outline-primary me-2"
                        onClick={() => navigate(`${uriEdit}${usuario.id}`)}
                      >
                        <EditIcon />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="alert alert-secondary text-center mt-5">
            Nenhum processo encontrado.
          </div>
        )}
      </div>

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

      <div
        className="modal fade"
        id="staticBackdrop"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabIndex={-1}
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h1 className="modal-title fs-5" id="staticBackdropLabel">
                Definir Data de Desativação
              </h1>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <form onSubmit={cadastrarDataAtivacaoDesativacao}>

                <div className={styles.inputGroup}>
                  <label className={styles.label}>
                    Data
                    <span className={styles.messageError}>
                      {messageDataError}
                    </span>
                  </label>
                  <input
                    className={styles.input}
                    placeholder="Data (DD/MM/AAAA)"
                    value={dataDeDesativacao}
                    onChange={(e) => formatarData(e.target.value)}
                    required
                  />
                </div>

                <div className={styles.inputGroup}>
                  <label className={styles.label}>Tipo Usuário</label>
                  <select
                    className={styles.input}
                    value={tipoUsuario}
                    onChange={selecionarTipoUsuario}
                  >
                    <option value="Estagiário">Estagiário</option>
                  </select>
                </div>

                <div className={styles.inputGroup}>
                  <label className={styles.label}>Usuário Status</label>
                  <select
                    className={styles.input}
                    value={usuarioStatus}
                    onChange={selecionarUsuarioStatus}
                  >
                    <option value="ATIVO">Ativo</option>
                    <option value="INATIVO">Inativo</option>
                  </select>
                </div>

              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Fechar
                </button>
                <button type="submit" className="btn btn-primary">
                  Concluir
                </button>
              </div>
              </form>
            </div>
          </div>
        </div>
              {/* Toast visual */}
      <ToastContainer position="top-end" className="p-3" style={{ zIndex: 9999 }}>
        <Toast
          onClose={() => setMostrarToast(false)}
          show={mostrarToast}
          bg={varianteToast}
          delay={3000}
          autohide
        >
          <Toast.Body className={`${styles.toastMessage} text-white`}>
            {mensagemToast}
          </Toast.Body>
        </Toast>
      </ToastContainer>
      </div>

      <footer className="text-center py-3 bg-dark text-white-50 small mt-auto">
        © {new Date().getFullYear()} Sistema Jurídico | Desenvolvido pelo LTD -
        Estácio.
      </footer>
    </div>
  );
}
