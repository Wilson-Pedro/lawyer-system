import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './screens/Home/Home';
import HomeAdmin from './screens/Home/HomeAdmin';
import HomeEstagiario from './screens/Home/HomeEstagiario';
import Processos from './screens/Processos/Processos';
import Cadastros from './screens/Cadastros/Cadastros';
import CadastrarAssistido from './screens/CadastrarAssistido/CadastrarAssistido';
import CadastrarEstagiario from './screens/CadastrarEstagiario/CadastrarEstagiario';
import CadastrarProcesso from './screens/CadastrarProcesso/CadastrarProcesso';
import CadastrarAdvogado from './screens/CadastrarAdvogado/CadastrarAdvogado';
import CadastrarMovimento from './screens/CadastrarMovimento/CadastrarMovimento';
import CadastrarUsuario from './screens/CadastrarUsuario/CadastrarUsuario';
import CadastrarDemanda from './screens/CadastrarDemanda/CadastrarDemanda';
import Movimento from './screens/Movimento/Movimento';
import EditarProcesso from './screens/EditarProcesso/EditarProcesso';
import MovimentarProcesso from './screens/MovimentarProcesso/MovimentarProcesso';
import Demandas from './screens/Demandas/Demandas';
import DemandasEstagiario from './screens/Demandas/DemandasEstagiario';
import Login from './screens/Login/Login';

export default function Rotas() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/home/admin" element={<HomeAdmin />} />
                <Route path="/home/estagiario" element={<HomeEstagiario />} />
                <Route path="/processos" element={<Processos />} />
                <Route path="/cadastrar/assistido" element={<CadastrarAssistido />} />
                <Route path="/cadastrar/estagiario" element={<CadastrarEstagiario />} />
                <Route path="/cadastrar/processo" element={<CadastrarProcesso />} />
                <Route path="/cadastrar/advogado" element={<CadastrarAdvogado />} />
                <Route path="/cadastrar/demanda" element={<CadastrarDemanda />} />
                <Route path="/cadastrar/usuario" element={ <CadastrarUsuario />} />
                <Route path="/processos/:numeroDoProcesso/movimento" element={<Movimento />}/>
                <Route path="/processos/:numeroDoProcesso/movimento/cadastrar" element={ <CadastrarMovimento /> } />
                <Route path="/processos/editar/:processoId"  element={ <EditarProcesso /> }/>
                <Route path="/movimentar" element={ <MovimentarProcesso />} />
                <Route path="/cadastrar" element={ <Cadastros />} />
                <Route path="/demandas" element={ <Demandas />} />
                <Route path="/demandas/:estagiarioId" element={ <DemandasEstagiario />} />
                <Route path='/login' element={ <Login /> } />
            </Routes>
        </BrowserRouter>
    );
}