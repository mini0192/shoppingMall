import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './Header';
import Add from './pages/Add';
import Read from './pages/read/Read';
import Home from './pages/Home'
import Put from './pages/Put';

function App() {
  return (
    <div style={{
      display: "grid",
      gridTemplateColumns: "1fr 800px 1fr",
      background: "#BFBFBF"
    }}>
      <div style={{
        gridColumnStart: "2",
        gridColumnEnd: "3",
        background: "#F2F2F2",
        height: "auto",
        paddingBottom: "300px",
        textAlign: "center"
      }}>
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
    </div>
  );
}

export default App;
