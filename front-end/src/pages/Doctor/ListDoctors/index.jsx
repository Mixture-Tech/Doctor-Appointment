import React from 'react'
import Doctor from "./components/Doctors";

export default function ListDoctors() {
  const doctors = [
    { id: 1, name: "TS, BS chuyên khoa II Chu Minh Hà", imageUrl: "https://cdn.bookingcare.vn/fo/w384/2021/05/20/100159-bs-chu-minh-ha.png", specialty: "Tim mạch, Nội khoa" },
    { id: 2, name: "GS, TS Thái Hồng Quang", imageUrl: "https://cdn.bookingcare.vn/fo/w256/2019/09/04/093422giao-su-thai-hong-quang.jpg", specialty: "Nội khoa" },
    { id: 3, name: "BS Chuyên khoa I Nguyễn Thị Thanh Xuân", imageUrl: "https://cdn.bookingcare.vn/fo/w256/2023/02/10/110232-bs-cki-nguy-n-th-thanh-xu-n-1.jpg", specialty: "Tim Mạch" },
    { id: 4, name: "ThS, BS Nguyễn Duy Khánh ", imageUrl: "https://cdn.bookingcare.vn/fo/w256/2022/01/22/095108-bs-nguyen-duy-khanh.png", specialty: "Nam học" },
    { id: 5, name: "PGS, TS, BS Nguyễn Khoa Diệu Vân", imageUrl: "https://cdn.bookingcare.vn/fo/w256/2022/04/05/155306-bs-dieu-van.jpg", specialty: "Tiểu đường - Nội tiết, Tuyến giáp" },
    { id: 6, name: "BS chuyên khoa II Phan Duy Kiên", imageUrl: "https://cdn.bookingcare.vn/fo/w256/2022/02/28/155511-bs-kien.png", specialty: "Tim mạch" },
  ];

  return (
      <main>
        <Doctor item={doctors} />
      </main>
  );
}
