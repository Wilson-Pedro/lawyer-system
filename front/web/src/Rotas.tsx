import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './screens/Home/Home';
import HomeAdmin from './screens/Home/HomeAdmin';
import HomeEstagiario from './screens/Home/HomeEstagiario';
import HomeAdvogado from './screens/Home/HomeAdvogado';
import Processos from './screens/Processos/Processos';

import Cadastros from './screens/Cadastros/Cadastros';
import CadastrarAssistido from './screens/CadastrarAssistido/CadastrarAssistido';
import CadastrarEstagiario from './screens/CadastrarEstagiario/CadastrarEstagiario';
import CadastrarProcesso from './screens/CadastrarProcesso/CadastrarProcesso';
import CadastrarAdvogado from './screens/CadastrarAdvogado/CadastrarAdvogado';
import CadastrarMovimento from './screens/CadastrarMovimento/CadastrarMovimento';
import CadastrarUsuario from './screens/CadastrarUsuario/CadastrarUsuario';
import CadastrarDemanda from './screens/CadastrarDemanda/CadastrarDemanda';
import CadastrarDemandaResposta from './screens/CadastrarDemanda/CadastrarDemandaResposta';
import Movimento from './screens/Movimento/Movimento';

import EditarProcesso from './screens/Editar/EditarProcesso';
import EditarAdvogado from './screens/Editar/EditarAdvogado';
import EditarAssistido from './screens/Editar/EditarAssistido';
import EditarEstagiario from './screens/Editar/EditarEstagiario';
import EditarUsuario from './screens/Editar/EditarUsuario';

import MovimentarProcesso from './screens/MovimentarProcesso/MovimentarProcesso';
import Demandas from './screens/Demandas/Demandas';
import EditarDemanda from './screens/Editar/EditarDemanda';
import DemandasEstagiario from './screens/Demandas/DemandasEstagiario';
import DemandaResposta from './screens/Demandas/DemandaResposta';
import DemandasAdvogado from './screens/Demandas/DemandasAdvogado';
import Usuarios from './screens/Usuarios/Usuarios';
import Login from './screens/Login/Login';

export default function Rotas() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/home/admin" element={<HomeAdmin />} />
                <Route path="/home/estagiario" element={<HomeEstagiario />} />
                <Route path="/home/advogado" element={<HomeAdvogado />} />
                <Route path="/processos" element={<Processos />} />
                <Route path="/cadastrar/assistido" element={<CadastrarAssistido />} />
                <Route path="/cadastrar/estagiario" element={<CadastrarEstagiario />} />
                <Route path="/cadastrar/processo" element={<CadastrarProcesso />} />
                <Route path="/cadastrar/advogado" element={<CadastrarAdvogado />} />
                <Route path="/cadastrar/demanda" element={<CadastrarDemanda />} />
                <Route path="/cadastrar/demanda/:demandaId/resposta" element={<CadastrarDemandaResposta />} />
                <Route path="/cadastrar/usuario" element={ <CadastrarUsuario />} />
                <Route path="/processos/:numeroDoProcesso/movimento" element={<Movimento />}/>
                <Route path="/processos/:numeroDoProcesso/movimento/cadastrar" element={ <CadastrarMovimento /> } />
                <Route path="/processos/editar/:processoId"  element={ <EditarProcesso /> }/>
                <Route path="/movimentar" element={ <MovimentarProcesso />} />
                <Route path="/cadastrar" element={ <Cadastros />} />
                <Route path="/demandas" element={ <Demandas />} />
                <Route path="/demandas/:demandaId/editar" element={ <EditarDemanda />} />
                <Route path="/demandas/estagiario/:estagiarioId" element={ <DemandasEstagiario />} />
                <Route path="/demandas/advogado/:advogadoId" element={ <DemandasAdvogado />} />
                <Route path="/demandas/:demandaId/respostas" element={ <DemandaResposta />} />
                <Route path="/usuarios" element={ <Usuarios /> } />
                <Route path="/usuarios/editar/:usuarioId" element={ <EditarUsuario /> } />
                <Route path="/usuarios/estagiario/editar/:estagiarioId" element={ <EditarEstagiario /> } />
                <Route path="/usuarios/advogado/editar/:advogadoId" element={ <EditarAdvogado /> } />
                <Route path="/usuarios/assistido/editar/:assistidoId" element={ <EditarAssistido /> } />
                <Route path='/login' element={ <Login /> } />
            </Routes>
        </BrowserRouter>
    );
}