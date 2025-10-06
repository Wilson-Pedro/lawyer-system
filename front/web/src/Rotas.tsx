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
            </Routes>
        </BrowserRouter>
    );
}