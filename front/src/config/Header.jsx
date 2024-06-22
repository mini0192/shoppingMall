import { useContext } from "react";
import { Link } from "react-router-dom";
import { NAME } from "./Context";


function Header() {
    const [contextName, setContextName] = useContext(NAME)

    function Login() {
        if(localStorage.getItem("name") !== null) {
            return(
                <Link className="nav-link" onClick={() => {
                    localStorage.clear();
                    setContextName("")
                }}>
                    Logout
                </Link>
            )
        }
        return(<Link className="nav-link" to="/member/login">Login</Link>)
    }

    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary paddingObj shadow-sm p-3 mb-5 bg-body-tertiary rounded">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/"><h3 className="pointColorObj">MINI</h3></Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div className="navbar-nav">
                        <Link className="nav-link" aria-current="page" to="/">Home</Link>
                        <Link className="nav-link" to="/shopping">Shopping</Link>
                        <Link className="nav-link" to="/admin">Manager</Link>
                        <Login/>
                    </div>
                </div>
                { contextName }
            </div>
        </nav>
    )
}

export default Header;