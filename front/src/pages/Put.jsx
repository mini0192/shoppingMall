import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function Put() {
    const p = useParams();
    const id = p.id;

    const [image, setImage] = useState([])
    
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [files, setFiles] = useState([]);
    const [item, setItem] = useState({});

    useEffect(() => {
        axios.get("http://localhost:8080/items/"+id)
        .then(response => {
            const data = response.data
            if(data == '') return;
            setItem(data);
            setImage(data.previewImage);
            console.log(data);
        })}, []
    );

    const submit = () => {
        setItem({
          name: name,
          price: price
        });

        const formData = new FormData();
        if(files == '') {
            formData.append("previewImage", image)
        } else {
            files.map(file => {
                formData.append("previewImage", file)
            });
        }

        formData.append("item", new Blob([JSON.stringify(item)], {type: "application/json"}))
        
        axios.post("http://localhost:8080/items", formData)
        .then(response => {
          const object = response.data;
          console.log(object);
        })
        .catch(response => {
          console.log(response);
        })
      }

    return (
    <div>
        <input type="text" name="name" value={ item.name }
        onChange={(e) => { 
            setName(e.target.value);
            setItem({
            name: name,
            price: price
            });
            console.log("image")
            console.log(image)
            console.log("files")
            console.log(files)
        }}/><br/>

        <input type="text" name="price" value={ item.price }
        onChange={(e) => { 
            setPrice(e.target.value);
            setItem({
            name: name,
            price: price
            });
        }}/><br/>

        <input type="file" name="imageInput" multiple accept='image/*'
        onChange={(e) => {
            console.log(e.target.files)
            setFiles(Array.from(e.target.files));
        }}/><br/>

        <input type="button" value="submit" onClick={ submit }/>
        </div>
    )
}

export default Put;