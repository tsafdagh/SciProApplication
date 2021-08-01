package com.ecomm.sciproapplication.repository.student

import com.ecomm.sciproapplication.dataSource.student.StudentDataSource
import javax.inject.Inject

class StudentRepository @Inject constructor(val dataSource: StudentDataSource) {
}