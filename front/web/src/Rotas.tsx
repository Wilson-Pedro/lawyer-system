import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './screens/Home/Home';
import HomeAdmin from './screens/HomeAdmin/HomeAdmin';
import Processos from './screens/Processos/Processos';
import CadastrarAssistido from './screens/CadastrarAssistido/CadastrarAssistido';
import CadastrarEstagiario from './screens/CadastrarEstagiario/CadastrarEstagiario';
import CadastrarProcesso from './screens/CadastrarProcesso/CadastrarProcesso';
import CadastrarAdvogado from './screens/CadastrarAdvogado/CadastrarAdvogado';
import CadastrarMovimento from './screens/CadastrarMovimento/CadastrarMovimento';
import Movimento from './screens/Movimento/Movimento';
import EditarProcesso from './screens/EditarProcesso/EditarProcesso';
import MovimentarProcesso from './screens/MovimentarProcesso/MovimentarProcesso';
import Cadastros from './screens/Cadastros/Cadastros';

export default function Rotas() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/admin" element={<HomeAdmin />} />
                <Route path="/processos" element={<Processos />} />
                <Route path="/cadastrarAssistido" element={<CadastrarAssistido />} />
                <Route path="/cadastrarEstagiario" element={<CadastrarEstagiario />} />
                <Route path="/cadastrarProcesso" element={<CadastrarProcesso />} />
                <Route path="/cadastrarAdvogado" element={<CadastrarAdvogado />} />
                <Route path="/processos/:numeroDoProcesso/movimento" element={<Movimento />}/>
                <Route path="/cadastrarMovimento/:numeroDoProcesso" element={ <CadastrarMovimento /> } />
                <Route path="/processos/editar/:processoId"  element={ <EditarProcesso /> }/>
                <Route path="/movimentar" element={ <MovimentarProcesso />} />
                <Route path="/cadastrar" element={ <Cadastros />} />
            </Routes>
        </BrowserRouter>
    );
}