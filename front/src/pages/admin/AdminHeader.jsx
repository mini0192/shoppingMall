import { useState } from "react";
import { Link } from "react-router-dom";

function AdminHeader() {

    const [id, setId] = useState(0);

    const setClass = (num) => {
        if(id === num) {
            return "active"
        }
        return ""
    }

    return (
        <ul className="nav nav-tabs justify-content-center">
            <li className="nav-item">
                <Link className={`nav-link ${setClass(0)}`} to="#" onClick={() => setId(0)}>관리자 페이지</Link>
            </li>
            <li className="nav-item">
                <Link className={`nav-link ${setClass(1)}`} to="/admin/itemAdd" onClick={() => setId(1)}>상품 추가</Link>
            </li>
            <li className="nav-item">
                <Link className={`nav-link ${setClass(2)}`} to="/admin/itemList" onClick={() => setId(2)}>상품 목록</Link>
            </li>
            <li className="nav-item">
                <Link className={`nav-link ${setClass(3)}`} to="/admin/memberList" onClick={() => setId(3)}>유저 목록</Link>
            </li>
        </ul>
    )
}

export default AdminHeader