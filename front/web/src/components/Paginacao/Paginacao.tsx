import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

interface PaginacaoProp {
    page:number;
    totalPages: number;
    paginaAtual: number;
    paginas: number[];
    // proximaPagina: any;
    // paginaAnterior: any;
    // navegarParaPagina: any;
    setPaginaAtual: any;
    setPage: any;
    mostrarPrimeiraPagina: boolean;
    mostrarUltimaPagina: boolean;
}

const Paginacao: React.FC<PaginacaoProp> = ({
    page,
    totalPages,
    paginaAtual,
    paginas,
    // proximaPagina,
    // paginaAnterior,
    // navegarParaPagina,
    setPaginaAtual,
    setPage,
    mostrarPrimeiraPagina,
    mostrarUltimaPagina
}) => {


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
