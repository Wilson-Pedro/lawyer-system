import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./Login.module.css";

const API_URL = process.env.REACT_APP_API;

export default function Login() { 
    const navigate = useNavigate();

    localStorage.clear();

    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");

    const login = async (e:any) => {
        e.preventDefault();
        try {
            const response = await axios.post(`${API_URL}/auth/login`, {
                login: email,
                password: senha
            }, {
                withCredentials: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            })

            if(response.data !== null) {
                const data = response.data;
                localStorage.setItem('token', data.token);
                localStorage.setItem('role', data.role);
                if(data.role === 'ADMIN') {
                    goToHomeAdmin();
                } else if(data.role === 'ESTAGIARIO') {
                    goToHomeEstagiario();
                }
            }

        } catch(error) {
            console.log(error)
        }

    }

    const goToHomeAdmin = () => {
        navigate("/home/admin")
    }

    const goToHomeEstagiario = () => {
        navigate("/home/estagiario")
    }

    // const limparCampos = () => {
    //     setEmail("");
    //     setEmail("");
    // };

    return (

        <div className={styles.container}>
            <h1 className={styles.title}>Login</h1>
            <form onSubmit={login}>
                <div className={styles.inputGroup}>
                    <label className={styles.label}>Email</label>
                    <input
                        className={styles.input}
                        placeholder="Email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div className={styles.inputGroup}>
                    <label className={styles.label}>Senha</label>
                    <input
                        className={styles.input}
                        placeholder="Senha"
                        type="password"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                    />
                </div>
                <div className={styles.divBtn}>
                    <button type="submit" className={styles.button}>
                        Entrar
                    </button>
                    <button type="button" onClick={() => navigate('/')} className={styles.button}>
                        Cancelar
                    </button>
                </div>
            </form>
        </div>

    );
}
