import { Link } from "react-router-dom";

function Header() {
    return (
        <div>
            <Link to="/">HOME</Link><br/>
            <Link to="/add">ADD ITEM</Link><br/>
            <Link to="/read">SHOW ITEMS</Link><br/>
            <br/>
        </div>
    )
}

export default Header;