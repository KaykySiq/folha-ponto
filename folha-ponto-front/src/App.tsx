import './App.css'
import { Routes, Route, HashRouter } from "react-router-dom";
import TelaInicial from './features/folhaPonto/pages/TelaInicial/TelaInicial';
import ListaFuncionarios from './features/folhaPonto/pages/ListaFuncionarios/ListaFuncionarios';

function App() {
  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<TelaInicial />} />
        <Route path="/funcionarios" element={<ListaFuncionarios />} />
      </Routes>
    </HashRouter>
  );
}

export default App;
