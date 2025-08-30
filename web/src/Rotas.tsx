import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from './screens/Home/Home';
import HomeAdmin from './screens/HomeAdmin/HomeAdmin';
import Processos from './screens/Processos/Processos';
import CadastrarAssistido from './screens/CadastrarAssistido/CadastrarAssistido';
import CadastrarEstagiario from './screens/CadastrarEstagiario/CadastrarEstagiario';
import CadastrarProcesso from './screens/CadastrarProcesso/CadastrarProcesso';

export default function Rotas() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/admin" element={<HomeAdmin />} />
                <Route path="/processos" element={<Processos />} />
                <Route path="/CadastrarAssistido" element={<CadastrarAssistido />} />
                <Route path="/CadastrarEstagiario" element={<CadastrarEstagiario />} />
                <Route path="/CadastrarProcesso" element={<CadastrarProcesso />} />
            </Routes>
        </BrowserRouter>
    );
}