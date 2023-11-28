// 사용할 데이터베이스 선택
db = db.getSiblingDB('testing');
// testing 데이터베이스에 사용자 생성
db.createUser({
  user: 'testinguser',
  pwd: 'password',
  roles: [
    {
      role: 'readWrite',
      db: 'testing'
    }
  ]
});

// 데이터베이스에 컬렉션 생성
db.createCollection('employees');

// 컬렉션에 초기 데이터 추가
db.employees.insert({
    _id: '1',
    email: 'aa@a.com'
});