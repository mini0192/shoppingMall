import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './Header';
import Add from './pages/Add';
import Read from './pages/read/Read';
import Home from './pages/Home'
import Put from './pages/Put';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Header/>
        <Routes>
          <Route path="/" element={<Home/>}></Route>
          <Route path="/add" element={<Add />}></Route>
          <Route path="/read/*" element={<Read />}></Route>
          <Route path="/put/:id" element={<Put/>}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
