import React, { useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

interface PaginacaoProp {
    page:number;
    totalPages: number;
    paginaAtual: number;
    primeiraPagina:number;
    ultimaPagina:number;
    paginas: number[];
    setPaginaAtual: any;
    setPrimeiraPagina:any;
    setUltimaPagina:any;
    setPage: any;
    mostrarPrimeiraPagina: boolean;
    mostrarUltimaPagina: boolean;
    setMostrarUltimaPagina:any;
    setMostrarPrimeiraPagina: any,
    setPaginas:any;
}

const Paginacao: React.FC<PaginacaoProp> = ({
    page,
    totalPages,
    paginaAtual,
    primeiraPagina,
    ultimaPagina,
    paginas,
    setPaginaAtual,
    setPrimeiraPagina,
    setUltimaPagina,
    setPage,
    mostrarPrimeiraPagina,
    mostrarUltimaPagina,
    setMostrarUltimaPagina,
    setMostrarPrimeiraPagina,
    setPaginas,
}) => {

    useEffect(() => {
          definirPaginacao();
    }, [paginaAtual, totalPages, ultimaPagina]);

      const definirPaginacao = () => {
        let pagina = primeiraPagina;
        let paginas: number[] = [];

        if(paginaAtual + 1 === 1) {
            setPrimeiraPagina(0);
            setUltimaPagina(totalPages <= 11 ? totalPages : 12);
            setMostrarUltimaPagina(true);
            setMostrarPrimeiraPagina(false);
        }


        if(totalPages <= 12) {
            setUltimaPagina(totalPages)
            setPrimeiraPagina(0)
            setMostrarPrimeiraPagina(false);
            setMostrarUltimaPagina(false);
        }


        if(paginaAtual === primeiraPagina) {

            if(paginaAtual - 10 > 10) {
                setUltimaPagina(paginaAtual + 2);
                setPrimeiraPagina(paginaAtual - 11);
                setMostrarPrimeiraPagina(true);
                setMostrarUltimaPagina(true);

            } else if(paginaAtual > 12) {
                setPrimeiraPagina(paginaAtual - 10);
                setUltimaPagina(paginaAtual + 2);
                setMostrarUltimaPagina(totalPages <= 12 ? false : true);
                setMostrarPrimeiraPagina(true);

            } else if(paginaAtual - 10 < 12) {
                setPrimeiraPagina(0);
                setUltimaPagina(totalPages <= 12 ? totalPages : 12);
                setMostrarUltimaPagina(totalPages <= 12 ? false : true);
                setMostrarPrimeiraPagina(false);
            }

        } else if (paginaAtual + 1 === ultimaPagina) {

            if((paginaAtual + 1 === ultimaPagina) && paginaAtual + 1 !== totalPages) {
                setMostrarUltimaPagina(true);
                setMostrarPrimeiraPagina(true);
            }

            if((totalPages - 1) - ultimaPagina > 10) {
                setMostrarUltimaPagina(true);
                setPrimeiraPagina(paginaAtual - 1);
                setUltimaPagina(paginaAtual + 10);

            } else if((totalPages - 1) - ultimaPagina <= 10) {
                setMostrarUltimaPagina(false);
                setMostrarPrimeiraPagina(totalPages - 13 > 0 ? true : false);
                setPrimeiraPagina(totalPages - 13 > 0 ? totalPages - 13 : 0);
                setUltimaPagina(totalPages);

            } else if (paginaAtual + 1 === totalPages) {
                setPrimeiraPagina(totalPages - 10);
                setUltimaPagina(totalPages);
                setMostrarUltimaPagina(false);
                setMostrarPrimeiraPagina(true);
            }

        } else if (paginaAtual + 1 === totalPages) {
                setPrimeiraPagina(totalPages - 13);
                setUltimaPagina(totalPages);
                setMostrarUltimaPagina(false);
                setMostrarPrimeiraPagina(true);
        }


        for(let i = primeiraPagina; i < ultimaPagina; i++) {
            paginas.push(pagina);
            pagina++;
        }
        setPaginas(paginas);
    }


    const paginaAnterior = () => {
        setPaginaAtual(page - 1);
        setPage(paginaAtual - 1);
    }

    const proximaPagina = () => {
        setPaginaAtual(page + 1);
        setPage(paginaAtual + 1);
    }

    const navegarParaPagina = (pagina:number) => {
        if(pagina >=0 && pagina <= totalPages) {
            setPage(pagina);
            setPaginaAtual(pagina);
        }
    }

  return (
    <nav aria-label="Page navigation example">
      <ul className="pagination justify-content-center">
        <li className="page-item">
          <button
            className={`page-link ${page === 0 ? "disabled" : ""}`}
            onClick={page === 0 ? undefined : paginaAnterior}
            disabled={page === 0}
          >
            Anterior
          </button>
        </li>

        {totalPages <= 11 ? (
          <>
            {paginas.map((item, index) => (
              <li className="page-item" key={index}>
                <button
                  className={`page-link ${page === item ? "active" : ""}`}
                  onClick={() => navegarParaPagina(item)}
                >
                  {page === item ? paginaAtual + 1 : item + 1}
                </button>
              </li>
            ))}
          </>
        ) : (
          <>
            {mostrarPrimeiraPagina && (
              <>
                <li className="page-item">
                  <button
                    className={`page-link`}
                    onClick={() => navegarParaPagina(0)}
                  >
                    1
                  </button>
                </li>
                <li className="page-item">
                  <button className="page-link disabled">...</button>
                </li>
              </>
            )}

            {paginas.map((item, index) => (
              <li className="page-item" key={index}>
                <button
                  className={`page-link ${page === item ? "active" : ""}`}
                  onClick={() => navegarParaPagina(item)}
                >
                  {item + 1}
                </button>
              </li>
            ))}

            {mostrarUltimaPagina && (
              <>
                <li className="page-item">
                  <button className="page-link disabled">...</button>
                </li>
                <li className="page-item">
                  <button
                    className={`page-link ${page === totalPages - 1 ? "active" : ""}`}
                    onClick={() => navegarParaPagina(totalPages - 1)}
                  >
                    {totalPages}
                  </button>
                </li>
              </>
            )}
          </>
        )}

        <li className="page-item">
          <button
            className={`page-link ${page === totalPages - 1 ? "disabled" : ""}`}
            onClick={page === totalPages - 1 ? undefined : proximaPagina}
            disabled={page === totalPages - 1}
          >
            Próximo
          </button>
        </li>
      </ul>
    </nav>
  );
};

export default Paginacao;
