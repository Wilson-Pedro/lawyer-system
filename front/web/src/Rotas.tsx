import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './screens/Home/Home';
import HomeAdmin from './screens/HomeAdmin/HomeAdmin';
import Processos from './screens/Processos/Processos';
import Cadastros from './screens/Cadastros/Cadastros';
import CadastrarAssistido from './screens/CadastrarAssistido/CadastrarAssistido';
import CadastrarEstagiario from './screens/CadastrarEstagiario/CadastrarEstagiario';
import CadastrarProcesso from './screens/CadastrarProcesso/CadastrarProcesso';
import CadastrarAdvogado from './screens/CadastrarAdvogado/CadastrarAdvogado';
import CadastrarMovimento from './screens/CadastrarMovimento/CadastrarMovimento';
import CadastrarUsuario from './screens/CadastrarUsuario/CadastrarUsuario';
import Movimento from './screens/Movimento/Movimento';
import EditarProcesso from './screens/EditarProcesso/EditarProcesso';
import MovimentarProcesso from './screens/MovimentarProcesso/MovimentarProcesso';

export default function Rotas() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/admin" element={<HomeAdmin />} />
                <Route path="/processos" element={<Processos />} />
                <Route path="/cadastrar/assistido" element={<CadastrarAssistido />} />
                <Route path="/cadastrar/estagiario" element={<CadastrarEstagiario />} />
                <Route path="/cadastrar/processo" element={<CadastrarProcesso />} />
                <Route path="/cadastrar/advogado" element={<CadastrarAdvogado />} />
                <Route path="/processos/:numeroDoProcesso/movimento" element={<Movimento />}/>
                <Route path="/processos/:numeroDoProcesso/movimento/cadastrar" element={ <CadastrarMovimento /> } />
                <Route path="/processos/editar/:processoId"  element={ <EditarProcesso /> }/>
                <Route path="/movimentar" element={ <MovimentarProcesso />} />
                <Route path="/cadastrar" element={ <Cadastros />} />
                <Route path="/cadastrar/usuario" element={ <CadastrarUsuario />} />
            </Routes>
        </BrowserRouter>
    );
}