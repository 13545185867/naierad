package com.jeecg.p3.system.web.back;

import com.jeecg.p3.system.entity.JwSystemProject;
import com.jeecg.p3.system.service.JwSystemProjectService;
import com.jeecg.p3.system.util.ContextHolderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping({"/system/back/jwSystemProject"})
public class JwSystemProjectController extends BaseController
{

  @Autowired
  private JwSystemProjectService jwSystemProjectService;

  @RequestMapping(value={"list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void list(@ModelAttribute JwSystemProject query, HttpServletResponse response, @RequestParam(required=false, value="pageNo", defaultValue="1") int pageNo, @RequestParam(required=false, value="pageSize", defaultValue="10") int pageSize)
    throws Exception
  {
    PageQuery pageQuery = new PageQuery();
    pageQuery.setPageNo(pageNo);
    pageQuery.setPageSize(pageSize);
    VelocityContext velocityContext = new VelocityContext();
    pageQuery.setQuery(query);
    velocityContext.put("query", query);
    velocityContext.put("pageInfos", SystemTools.convertPaginatedList(this.jwSystemProjectService.queryPageList(pageQuery)));
    String viewName = "system/back/jwSystemProject-list.vm";
    ViewVelocity.view(response, viewName, velocityContext);
  }

  @RequestMapping(value={"toDetail"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void jwSystemProjectDetail(@RequestParam(required=true, value="id") String id, HttpServletResponse response)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    String viewName = "system/back/jwSystemProject-detail.vm";
    JwSystemProject jwSystemProject = this.jwSystemProjectService.queryById(id);
    velocityContext.put("jwSystemProject", jwSystemProject);
    ViewVelocity.view(response, viewName, velocityContext);
  }

  @RequestMapping(value={"/toAdd"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void toAddDialog(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    String viewName = "system/back/jwSystemProject-add.vm";
    ViewVelocity.view(response, viewName, velocityContext);
  }

  @RequestMapping(value={"/doAdd"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson doAdd(@ModelAttribute JwSystemProject jwSystemProject)
  {
    AjaxJson j = new AjaxJson();
    try
    {
      Boolean repeat = this.jwSystemProjectService.validReat(jwSystemProject.getCode(), jwSystemProject.getId());
      if (repeat.booleanValue()) {
        j.setSuccess(false);
        j.setMsg("项目编码重复，请重新设置");
      } else {
        this.jwSystemProjectService.doAdd(jwSystemProject);
        j.setMsg("保存成功");
      }
    } catch (Exception e) {
      j.setSuccess(false);
      j.setMsg("保存失败");
    }
    return j;
  }

  @RequestMapping(value={"toEdit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void toEdit(@RequestParam(required=true, value="id") String id, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    JwSystemProject jwSystemProject = this.jwSystemProjectService.queryById(id);
    velocityContext.put("jwSystemProject", jwSystemProject);
    String viewName = "system/back/jwSystemProject-edit.vm";
    ViewVelocity.view(request, response, viewName, velocityContext);
  }

  @RequestMapping(value={"/doEdit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson doEdit(@ModelAttribute JwSystemProject jwSystemProject)
  {
    AjaxJson j = new AjaxJson();
    try
    {
      Boolean repeat = this.jwSystemProjectService.validReat(jwSystemProject.getCode(), jwSystemProject.getId());
      if (repeat.booleanValue()) {
        j.setSuccess(false);
        j.setMsg("项目编码重复，请重新设置");
      } else {
        this.jwSystemProjectService.doEdit(jwSystemProject);
        j.setMsg("编辑成功");
      }
    } catch (Exception e) {
      j.setSuccess(false);
      j.setMsg("编辑失败");
    }
    return j;
  }

  @RequestMapping(value={"doDelete"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public AjaxJson doDelete(@RequestParam(required=true, value="id") String id)
  {
    AjaxJson j = new AjaxJson();
    try {
      this.jwSystemProjectService.doDelete(id);
      j.setMsg("删除成功");
    } catch (Exception e) {
      j.setSuccess(false);
      j.setMsg("删除失败");
    }
    return j;
  }

  @RequestMapping(value={"/doUpload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson doUpload(MultipartHttpServletRequest request, HttpServletResponse response)
  {
    AjaxJson j = new AjaxJson();
    try {
      MultipartFile uploadify = request.getFile("file");
      byte[] bytes = uploadify.getBytes();
      System.out.println(uploadify.getOriginalFilename());
      String uploadDir = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("upload/img/jlb");
      File dirPath = new File(uploadDir);
      if (!dirPath.exists()) {
        dirPath.mkdirs();
      }
      String sep = System.getProperty("file.separator");
      Date date = new Date();
      String body="";  
      String ext="";  
      int pot=uploadify.getOriginalFilename().lastIndexOf(".");  
      if(pot!=-1){  
          body= date.getTime() +"";  
          ext=uploadify.getOriginalFilename().substring(pot);  
      }else{  
          body=(new Date()).getTime()+"";  
          ext="";  
      }  
      String newName=body+ext;  
      File uploadedFile = new File(uploadDir + sep + newName);

      FileCopyUtils.copy(bytes, uploadedFile);
      if(ext.equals(".txt")){
    	  newName = new String(bytes,"utf-8");
      }
      if(ext.equals(".xls") ){
 
    	  StringBuffer sb = new StringBuffer();
    	    POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(uploadedFile));
    	    HSSFWorkbook hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);
    	    HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

    	    int rowstart = hssfSheet.getFirstRowNum();
    	    int rowEnd = hssfSheet.getLastRowNum();
    	    for(int i=rowstart;i<=rowEnd;i++)
    	    {
    	        HSSFRow row = hssfSheet.getRow(i);
    	        if(null == row) continue;
    	        int cellStart = row.getFirstCellNum();
    	        int cellEnd = row.getLastCellNum();
	            if(i!=0){
	            	 sb.append(",");
	            }
    	        for(int k=cellStart;k<=cellEnd;k++)
    	        {
    	            HSSFCell cell = row.getCell(k);
    	            if(null==cell) continue;
    	            switch (cell.getCellType())
    	            {
    	                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
    	                	BigDecimal bd = new BigDecimal(cell.getNumericCellValue()); 
    	                    sb.append(bd.toPlainString());
    	                    break;
    	                case HSSFCell.CELL_TYPE_STRING: // 字符串
    	                	sb.append(cell.getStringCellValue());
    	                    break;
    	                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
    	                	sb.append(cell.getBooleanCellValue());
    	                    break;
    	                case HSSFCell.CELL_TYPE_FORMULA: // 公式
    	                	 sb.append(cell.getCellFormula());
    	                    break;
    	                case HSSFCell.CELL_TYPE_BLANK: // 空值
    	                    break;
    	                case HSSFCell.CELL_TYPE_ERROR: // 故障
    	                    break;
    	                default:
    	                    break;
    	            }
    	            
    	            
    	        }
    	    }
    	   	  newName = sb.toString();
      }
      if( ext.equals(".xlsx") ){
    	  StringBuffer sb = new StringBuffer();
    	  XSSFWorkbook xssfWorkbook = new XSSFWorkbook(uploadDir + sep + newName);
    	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

    	    int rowstart = xssfSheet.getFirstRowNum();
    	    int rowEnd = xssfSheet.getLastRowNum();
    	    for(int i=rowstart;i<=rowEnd;i++)
    	    {
    	        XSSFRow row = xssfSheet.getRow(i);
    	        if(null == row) continue;
    	        int cellStart = row.getFirstCellNum();
    	        int cellEnd = row.getLastCellNum();
    	        
    	        
	            if(i!=0){
 	            	 sb.append(",");
 	            }

    	        for(int k=cellStart;k<=cellEnd;k++)
    	        {
    	            XSSFCell cell = row.getCell(k);
    	            if(null==cell) continue;
    	            
    	            switch (cell.getCellType())
    	            {
    	                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
    	                     
    	                	BigDecimal bd = new BigDecimal(cell.getNumericCellValue()); 
    	                    sb.append(bd.toPlainString());
    	                    break;
    	                case HSSFCell.CELL_TYPE_STRING: // 字符串
    	                    sb.append(cell.getStringCellValue());
    	                    break;
    	                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
    	                    sb.append(cell.getBooleanCellValue());
    	                    break;
    	                case HSSFCell.CELL_TYPE_FORMULA: // 公式
    	                    sb.append(cell.getCellFormula());
    	                    break;
    	                case HSSFCell.CELL_TYPE_BLANK: // 空值
    	                    break;
    	                case HSSFCell.CELL_TYPE_ERROR: // 故障
    	                    break;
    	                default:
    	                    break;
    	            }
    	            


    	        }

    	    }
    	    newName = sb.toString();
      }

      j.setMsg("保存成功");
      j.setObj(newName);
    } catch (Exception e) {
      j.setSuccess(false);
      j.setMsg("保存失败");
    }
    return j;
  }

  @RequestMapping(value={"/getProjList"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson recieveState(HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    try {
      PageQuery pageQuery = new PageQuery();
      JwSystemProject query = new JwSystemProject();
      pageQuery.setPageNo(1);
      pageQuery.setPageSize(100);
      pageQuery.setQuery(query);
      PageList projectList = this.jwSystemProjectService.queryPageList(pageQuery);

      JSONArray result = new JSONArray();
      if ((projectList != null) && (projectList.getValues() != null) && (projectList.getValues().size() > 0)) {
        List pList = projectList.getValues();
        for (int i = 0; i < pList.size(); i++) {
          JSONObject jsonParts = new JSONObject();
          jsonParts.put("name", ((JwSystemProject)pList.get(i)).getName());
          jsonParts.put("img", ((JwSystemProject)pList.get(i)).getImg());
          result.add(jsonParts);
        }
      }
      Map attrs = new HashMap();
      attrs.put("projectList", result);
      j.setAttributes(attrs);
      j.setSuccess(true);
    } catch (Exception e) {
      j.setSuccess(false);
      j.setMsg("访问异常！");
      this.LOG.error("recieveState error:{}", e.getMessage());
    }
    return j;
  }
}