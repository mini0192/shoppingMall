import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function ReadList() {
    
    const [data, setData] = useState([])


    useEffect(() => {
        axios.get("http://localhost:8080/items")
        .then(response => {
            const data = response.data.content
            if(data == '') return;
            setData(data);
            console.log(data);
        })}, []
    );

    return (
        <div>
            {data.map(item => (
                <div key = { item.id } >
                    <br/>
                    <img src={"http://localhost:8080/" + item.previewImage[0] } width='500'/>
                    <Link key = { item.id } to={ `/read/${ item.id }`}> <h3> { item.name } </h3> </Link>
                    <p> { item.price }원 </p>
                    <hr/>
                </div>
            ))}
        </div>
    )
}

export default ReadList;