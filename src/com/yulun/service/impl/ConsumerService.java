package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.ConsumerManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("consumerService")
public class ConsumerService implements ConsumerManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public List<PageData> findgraduatelistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findgraduatelistPage", page);
    }

    @Override
    public List<PageData> findgraduate(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findgraduate", page);
    }


    @Override
    public void insertgraduate(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertgraduate",pd);
    }

    @Override
    public void updategraduate(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updategraduate",pd);
    }

    @Override
    public void deletegraduate(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deletegraduate",pd);
    }

    @Override
    public List<PageData> findpovertylistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findpovertylistPage", page);
    }

    @Override
    public List<PageData> findpoverty(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findpoverty", pd);
    }


    @Override
    public void insertpoverty(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertpoverty",pd);
    }

    @Override
    public void updatepoverty(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updatepoverty",pd);
    }

    @Override
    public void deletepoverty(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deletepoverty",pd);
    }

    @Override
    public List<PageData> findsbbtrylistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findsbbtrylistPage", page);
    }

    @Override
    public List<PageData> findsbbtry(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findsbbtry", pd);
    }


    @Override
    public void insertsbbtry(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertsbbtry",pd);
    }

    @Override
    public void updatesbbtry(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updatesbbtry",pd);
    }

    @Override
    public void deletesbbtry(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deletesbbtry",pd);
    }

    @Override
    public List<PageData> findjzbtrylistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findjzbtrylistPage", page);
    }

    @Override
    public List<PageData> findjzbtry(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findjzbtry", page);
    }


    @Override
    public void insertjzbtry(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertjzbtry",pd);
    }

    @Override
    public void updatejzbtry(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updatejzbtry",pd);
    }

    @Override
    public void deletejzbtry(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deletejzbtry",pd);
    }

    @Override
    public List<PageData> findcompanylistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findcompanylistPage", page);
    }

    @Override
    public List<PageData> findcomreglistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findcomreglistPage", page);
    }

    @Override
    public  List<PageData> findcompany(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findcompany", page);
    }

    @Override
    public void insertcompany(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertcompany",pd);
    }

    @Override
    public void updatecompany(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updatecompany",pd);
    }

    @Override
    public void deletecompany(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deletecompany",pd);
    }

    @Override
    public List<PageData> findemployreglistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findemployreglistPage", page);
    }

    @Override
    public List<PageData> findemployreg(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findemployreg", pd);
    }

    @Override
    public void insertemployreg(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertemployreg",pd);
    }

    @Override
    public void updateemployreg(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updateemployreg",pd);
    }

    @Override
    public void deleteemployreg(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deleteemployreg",pd);
    }

    @Override
    public List<PageData> getcompanytoday(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.getcompanytoday", page);
    }

    @Override
    public List<PageData> getpersontoday(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.getpersontoday", page);
    }

    @Override
    public List<PageData> getcalltoday(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.getcalltoday", page);
    }

    @Override
    public PageData getDIC(PageData page) throws Exception {
        return (PageData) dao.findForObject("ConsumerMapper.getDIC", page);
    }

    @Override
    public List<PageData> getDicAll(PageData page) throws Exception {
        return (List<PageData>) dao.findForList("ConsumerMapper.getDicAll", page);
    }

    @Override
    public PageData getjobtype(PageData page) throws Exception {
        return (PageData) dao.findForObject("ConsumerMapper.getjobtype", page);
    }

    @Override
    public void savePerson(PageData pd) throws Exception {
        dao.save("ConsumerMapper.savePerson", pd);
    }

    @Override
    public void savegraduate(PageData pd) throws Exception {
        dao.save("ConsumerMapper.savegraduate", pd);
    }

    @Override
    public void savegraduatedetai_error(PageData pd) throws Exception {
        dao.save("ConsumerMapper.savegraduatedetai_error", pd);
    }

    @Override
    public void editPerson(PageData pd) throws Exception {
        dao.update("ConsumerMapper.editPerson", pd);
    }

    @Override
    public void updatejobunit(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updatejobunit", pd);
    }



    @Override
    public void updateBatchByCardid(List<PageData> list) throws Exception {
        dao.batchSave("ConsumerMapper.updateBatchByCardid", list);
    }

    @Override
    public void updateBatchByTelAndName(List<PageData> list) throws Exception {
        dao.batchSave("ConsumerMapper.updateBatchByTelAndName", list);
    }

    @Override
    public void saveBatch(List<PageData> list) throws Exception {
        dao.batchSave("ConsumerMapper.saveBatch", list);
    }

    @Override
    public List<PageData> findExportList(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findExportlistPage", page);
    }

    @Override
    public List<PageData> findgraduateExport(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findGradeExportlistPage", page);
    }

    @Override
    public void batchSaveGrad(List<PageData> saveList) throws Exception {
        dao.batchSave("ConsumerMapper.gradeSaveBatch",saveList);
    }

    @Override
    public void gradeEditBatch(PageData pd) throws Exception {
        dao.update("ConsumerMapper.gradeEditBatch",pd);
    }

    @Override
    public List<PageData> findotherPersonexport(PageData page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findotherPersonexport", page);
    }

    @Override
    public List<PageData> findLoseJoblistPage(Page page) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findLoseJoblistPage", page);
    }

    @Override
    public List<PageData> findlostjobexport(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ConsumerMapper.findlostjobexport", pd);
    }

    @Override
    public void insertLoseJob(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertLoseJob",pd);
    }

    @Override
    public void updateLoseJob(PageData pd) throws Exception {
        dao.update("ConsumerMapper.updateLoseJob",pd);
    }

    @Override
    public void deteleLoseJob(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.deteleLoseJob",pd);
    }

    @Override
    public void detelePerson(PageData pd) throws Exception {
        dao.delete("ConsumerMapper.detelePerson",pd);
    }

    @Override
    public void insertLoseJob_error(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertLoseJob_error",pd);
    }

    @Override
    public void savePerson_error(PageData pd) throws Exception {
        dao.save("ConsumerMapper.savePerson_error",pd);
    }

    @Override
    public void insertjobregerror(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertjobregerror",pd);
    }

    @Override
    public void insertcompany_error(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertcompany_error",pd);
    }

    @Override
    public void insertemployreg_error(PageData pd) throws Exception {
        dao.save("ConsumerMapper.insertemployreg_error",pd);
    }
}
