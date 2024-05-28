import { Link } from "react-router-dom";

function Header() {
    return (
        <div>
            <div style={{
                textAlign: "center",
                display: "grid",
                gridTemplateColumns: "1fr 500px 1fr",
                padding: "20px",
                }}>
                <div style={{
                        gridColumnStart: "2",
                        gridColumnEnd: "3",
                        display: "grid",
                        gridTemplateColumns: "1fr 10px 1fr 10px 1fr"
                    }}>
                    <Link to="/" style={{
                        gridColumnStart: "1",
                        gridColumnEnd: "2",
                    }}>HOME</Link>

                    <Link to="/add" style={{
                        gridColumnStart: "3",
                        gridColumnEnd: "4",
                    }}>ADD ITEM</Link>

                    <Link to="/read" style={{
                        gridColumnStart: "5",
                        gridColumnEnd: "6",
                    }}>SHOW ITEMS</Link>
                </div>
            </div>
            <hr/>
        </div>
    )
}

export default Header;