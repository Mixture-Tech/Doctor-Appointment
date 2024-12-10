import React from 'react'
import Speciality from "./components/Speciality";

export default function Specialities() {
  const specialties = [
    { id: 1, name: "Cơ Xương Khớp", imageUrl: "https://cdn.bookingcare.vn/fo/w640/2023/12/26/101627-co-xuong-khop.png" },
    { id: 2, name: "Thần kinh", imageUrl: "https://cdn.bookingcare.vn/fo/w640/2023/12/26/101739-than-kinh.png" },
    { id: 3, name: "Tiêu hoá", imageUrl: "https://cdn.bookingcare.vn/fo/w640/2023/12/26/101713-tieu-hoa.png" },
    { id: 4, name: "Tim mạch", imageUrl: "https://cdn.bookingcare.vn/fo/w640/2023/12/26/101713-tim-mach.png" },
    { id: 5, name: "Tai Mũi Họng", imageUrl: "https://cdn.bookingcare.vn/fo/w640/2023/12/26/101713-tai-mui-hong.png" },
    { id: 6, name: "Cột sống", imageUrl: "https://cdn.bookingcare.vn/fo/w640/2023/12/26/101627-cot-song.png" },
  ];

  return (
      <main>
          <Speciality item={specialties} />
      </main>
  );
}
