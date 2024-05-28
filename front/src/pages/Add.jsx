import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Add() {
  
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [files, setFiles] = useState([]);
  const [item, setItem] = useState({});

  const submit = () => {
    setItem({
      name: name,
      price: price
    });

    const formData = new FormData();

    files.map(file => {
      formData.append("previewImage", file)
    });

    formData.append("item", new Blob([JSON.stringify(item)], {type: "application/json"}))
    
    axios.post("http://localhost:8080/items", formData)
    .then(response => {
      const object = response.data;
      alert("생성이 완료되었습니다.")
      navigate(`/read/${object.id}`)
    })
    .catch(error => {
        alert("입력 값이 잘못되었습니다.")
    })
  }

  return (
    <div style={{
        padding: "30px 100px 0 100px",
        display: "grid",
        gridTemplateColumns: "2fr 1fr",
        gridTemplateRows: "300px 50px"
      }}>
      <input type="file" name="imageInput" multiple accept='image/*'
      style={{
        gridColumnStart: "1",
        gridColumnEnd: "2",

        gridRowStart: "1",
        gridRowEnd: "2"
      }}
      onChange={(e) => {
        console.log(e.target.files)
        setFiles(Array.from(e.target.files));
      }}/>


      <div style={{
          gridColumnStart: "2",
          gridColumnEnd: "3",
        
          gridRowStart: "1",
          gridRowEnd: "2"
        }}>

        <input type="text" name="name" placeholder='name'
        onChange={(e) => { 
          setName(e.target.value);
          setItem({
            name: name,
            price: price
          });
        }}/>

        <input type="text" name="price" placeholder='price'
        onChange={(e) => { 
          setPrice(e.target.value);
          setItem({
            name: name,
            price: price
          });
        }}/>
      </div>
      <input type="button" value="submit" onClick={ submit }
      style={{
        gridColumnStart: "1",
        gridColumnEnd: "3",

        gridRowStart: "2",
        gridRowEnd: "3"
      }}/>
    </div>
  );
}

export default Add;