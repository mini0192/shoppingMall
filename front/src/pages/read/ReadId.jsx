import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

function ReadId() {
    const p = useParams();
    const id = p.id;

    const navigate = useNavigate();

    const [item, setItem] = useState([])
    const [image, setImage] = useState([])


    useEffect(() => {
        axios.get("http://localhost:8080/items/"+id)
        .then(response => {
            const data = response.data
            if(data == '') return;
            setItem(data);
            setImage(data.previewImage);
        })}, []
    );

    function deleteId() {
        axios.delete("http://localhost:8080/items/"+id)
        .then(response => {
            alert("삭제가 완료되었습니다.");
            navigate("/read");
        })
        .catch(error => {
            console.log(error.response.data);
        })
    }

    return (
        <div>
            <div key = { item.id } >
                <div key = { image }>
                    { image.map(image => (
                        <img src={ "http://localhost:8080/" + image } width='500'/>
                    ))};
                </div>
                <h3> { item.name } </h3>
                <p> { item.price } </p>
            </div>
            <Link key = { id } to={`/put/${ id }`}>수정</Link>
            <input type="button" value="삭제" onClick={ deleteId }/>
        </div>
    )
}

export default ReadId;