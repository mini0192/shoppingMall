import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

function ReadId() {
    const p = useParams();
    const id = p.id;

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
                <hr/>
            </div>
            <Link key = { id } to={`/put/${ id }`}>수정</Link>
        </div>
    )
}

export default ReadId;